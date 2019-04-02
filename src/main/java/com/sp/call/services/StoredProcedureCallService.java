package com.sp.call.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.sp.call.dao.PortfolioDaoImpl;
import com.sp.call.model.InitiateBuildRequest;
import com.sp.call.model.Portfolio;
import com.sp.call.model.PortfolioResponse;
import com.sp.call.model.Request;

@Service
@EnableAsync
@Configuration
public class StoredProcedureCallService {

	public static Logger logger = LoggerFactory.getLogger(StoredProcedureCallService.class);

	@Autowired
	DataSource dataSource;
	
	@Autowired
	PortfolioDaoImpl portfolioDao;
	
	@Value("${create_portfolio_item_url}")
	String createPortfolioItemURL;
	
	
	@Autowired
	private RestTemplate http;

	/**
	 * This method executes sp_pi_initiate_build stored procedure and returns back
	 * with portfolio id.
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */

	public PortfolioResponse spPiInitiateBuild(InitiateBuildRequest req) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("OSSINT").withCatalogName("pkg_pi_portfolio_inventory")
				.withProcedureName("sp_pi_initiate_build");
		MapSqlParameterSource map = new MapSqlParameterSource().addValue("i_system_userid", req.getI_system_userid());
		map.addValue("i_bus_system_name", req.getI_bus_system_name());
		map.addValue("i_cust_gaid", req.getI_cust_gaid());
		map.addValue("i_userid", req.getI_userid());

		logger.debug("@@@ Excecuting sp_pi_initiate_build procedure with Params : " + req.toString());
		Map<String, Object> out = executeProcedure(jdbcCall, map);
		PortfolioResponse res = new PortfolioResponse();
		res.setPortfolio_id(getValue(out, "O_PORTFOLIO_ID", "sp_pi_initiate_build"));
		res.setSystem_interaction_id(getValue(out, "O_SYSTEM_INTERACTION_ID", "sp_pi_initiate_build"));
		return res;

	}

	@Async
	public void triggerAnotherProcedures(Request req,String token) throws Exception {
		
		spPiGroupProductsMain(req);
		spPiStagePortfolioMain(req);
		
		//2.c
				// 1. Will get json from db by table
				// 2. get the token from request
				// 3. post to sigma for new portfolio creation
		sendResultantJsonToSigma(req.getI_portfolio_id(), token);
	}

	

	/**
	 * This method executes sp_pi_group_products_main stored procedure and returns
	 * back with O_SYSTEM_INTERACTION_ID id.
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public int spPiGroupProductsMain(Request req) throws Exception {
		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("OSSINT").withCatalogName("pkg_pi_portfolio_inventory")
				.withProcedureName("sp_pi_group_products_main");
		MapSqlParameterSource map = new MapSqlParameterSource().addValue("i_portfolio_id", req.getI_portfolio_id());
		map.addValue("i_cust_gaid", req.getI_cust_gaid());
		map.addValue("i_system_userid", req.getI_system_userid());
		map.addValue("i_bus_system_name", req.getI_bus_system_name());
		map.addValue("i_userid", req.getI_userid());
		logger.debug("@@@ Excecuting sp_pi_group_product_main procedure with Params : " + req.toString());
		int sysInteractionId = 0;
		ExecutorService threadPool = Executors.newFixedThreadPool(1);
		try {
			logger.debug("@@@ WAITING on result from SP_PI_GROUP_PRODUCTS_MAIN procedure execution..... ");
			Future<Map<String, Object>> interactionIdFuture = threadPool.submit(new JdbcTask(jdbcCall, map));
			while (true) {
//				logger.debug("===== > isDone :" + interactionIdFuture.isDone());
				if (interactionIdFuture.isDone()) {
					try {
						Map<String, Object> out = interactionIdFuture.get();
						sysInteractionId = getValue(out, "O_SYSTEM_INTERACTION_ID", "sp_pi_group_products_main");
						break;
					} catch (Exception e) {
						logger.error("@@@ " + e.getMessage());
						throw e;
					}
				} else {
					Thread.sleep(5);
				}

			}
			logger.debug("@@@ SP_PI_GROUP_PRODUCTS_MAIN procedure execution is completed.\n System Interaction ID is : "
					+ sysInteractionId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {

			if (threadPool != null)
				threadPool.shutdown();
		}

		return sysInteractionId;

	}

	/**
	 * This method executes sp_pi_stage_portfolio_main stored procedure and returns
	 * back with O_SYSTEM_INTERACTION_ID id.
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */

	@Async
	public int spPiStagePortfolioMain(Request req) throws Exception {

//		Thread.sleep(5000);

		SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withSchemaName("OSSINT").withCatalogName("pkg_pi_portfolio_inventory")
				.withProcedureName("sp_pi_stage_portfolio_main");
		MapSqlParameterSource map = new MapSqlParameterSource().addValue("i_portfolio_id", req.getI_portfolio_id());
		map.addValue("i_cust_gaid", req.getI_cust_gaid());
		map.addValue("i_system_userid", req.getI_system_userid());
		map.addValue("i_bus_system_name", req.getI_bus_system_name());
		map.addValue("i_userid", req.getI_userid());
		logger.debug("@@@ Excecuting sp_pi_stage_portfolio_main procedure with Params : " + req.toString());

		int sysInteractionId = 0;
		ExecutorService threadPool = Executors.newFixedThreadPool(1);
		try {
			logger.debug("@@@ WAITING on result from SP_PI_STAGE_PORTFOLIO_MAIN procedure execution..... ");
			Future<Map<String, Object>> interactionIdFuture = threadPool.submit(new JdbcTask(jdbcCall, map));
			while (true) {
//				logger.debug("===== > isDone :" + interactionIdFuture.isDone());
				if (interactionIdFuture.isDone()) {
					try {
						Map<String, Object> out = interactionIdFuture.get();
						sysInteractionId = getValue(out, "O_SYSTEM_INTERACTION_ID", "sp_pi_stage_portfolio_main");
						break;
					} catch (Exception e) {
						logger.error("@@@ " + e.getMessage());
						throw e;
					}
				} else {
					Thread.sleep(5);
				}

			}
			logger.debug(
					"@@@ SP_PI_STAGE_PORTFOLIO_MAIN procedure execution is completed.\n System Interaction ID is : "
							+ sysInteractionId);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (threadPool != null)
				threadPool.shutdown();
		}

		return sysInteractionId;
	}

	private int getValue(Map<String, Object> out, String key, String procName) throws Exception {

		if (out != null) {
			Optional<BigDecimal> number = Optional.empty();
			try {
				number = Optional.of((BigDecimal) out.get(key));
			} catch (Exception e) {
				logger.error("@@@ not recieved NUMBER from procedure call : " + procName);
				throw new Exception("@@@ not recieved NUMBER from procedure call : " + procName);
			}
			if (number.isPresent()) {
				int sysIntId = number.get().intValue();
				if (sysIntId == 0)
					throw new Exception(procName + " procedure has return " + key + " value as ZERO");
				logger.debug("@@@ successfully acquired " + key + " id :  " + sysIntId);
				return sysIntId;// interactionId.get().intValue();
			} else {
				throw new Exception(procName + " proc has not return " + key + " id.");
			}
		} else {
			throw new Exception(procName + " proc has not return any output.");
		}

	}

	private Map<String, Object> executeProcedure(SimpleJdbcCall jdbcCall, MapSqlParameterSource map) throws Exception {
		return jdbcCall.execute(map);
	}
	
	
	
	private void sendResultantJsonToSigma(Integer portfolioId, String token) throws Exception {
		// fetch the resultant JSON from the Portfolio table 
		List<Portfolio> list = portfolioDao.getPortfolio(portfolioId);
		
		String jsonStr = "";
		if ( list != null && list.size() > 0 ) {
			jsonStr = list.get(0).getJson();
		}
		
		logger.debug("@@@  Portfolio Id : "+ portfolioId +",  & Before formatting JSON String => "+jsonStr);
		
		if ( !"".equals(jsonStr))
			jsonStr = prepareFinalJson(jsonStr);
		
		logger.debug("@@@  Final JSON data sending to SIGMA : \n " + jsonStr);
		
		// send final JSON to sigma with token
		String responseFromSigma = CreateOrUpdatePortfolio(jsonStr, token);
		logger.debug("@@@ Response from SIGMA :"+ responseFromSigma);
		
	}
	

	private String prepareFinalJson(String jsonStr) {
		 jsonStr = "{\r\n" + 
				"\"requestId\": \"KARL0001\",\r\n" + 
				"\"create\": {\r\n" + 
				"\"createPortfolioItem\": [{\r\n" + 
				"  \"portfolioItem\": [" + 
				jsonStr
				+ "]\r\n" + 
				"}]\r\n" + 
				"}\r\n" + 
				"}";
		 
		 return jsonStr;
				
	}

	private String CreateOrUpdatePortfolio(String jsonStr,String token) throws Exception{
		
		// set headers
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setBearerAuth(token);
			
			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("string", jsonStr);
			

			HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map, headers);

			// send request and parse result
			ResponseEntity<String> res = null;
			try {
				res = http.exchange(createPortfolioItemURL, HttpMethod.POST, entity, String.class);
				if ( res != null /* res.getStatusCode() == HttpStatus.CREATED || res.getStatusCode() == HttpStatus.OK*/) {
					return res.getBody();
				}/*else {
					throw new Exception("### Exception occured during creating portfolio in sigma system : "+res.getBody());
				}*/
				
				return null;
			} catch (HttpStatusCodeException se) {
			    logger.error(se.getResponseBodyAsString());
			    throw new Exception(se.getStatusText());
			}catch (Exception e) {
				logger.error(e.getMessage());
				throw e;
			}
			
	
}





	class JdbcTask implements Callable<Map<String, Object>> {

		MapSqlParameterSource taskMap;
		SimpleJdbcCall jdbcCall;

		public JdbcTask() {
		}

		public JdbcTask(SimpleJdbcCall jdbcCall, MapSqlParameterSource map) {
			this.taskMap = map;
			this.jdbcCall = jdbcCall;
		}

		@Override
		public Map<String, Object> call() throws Exception {
			Map<String, Object> m1 = null;
			if (jdbcCall != null) {
				try {
//					Thread.sleep(500);
					m1 = jdbcCall.execute(this.taskMap);
					return m1;
				} catch (Exception e) {
					throw e;
				}
			}
			return null;
		}

	}

}
