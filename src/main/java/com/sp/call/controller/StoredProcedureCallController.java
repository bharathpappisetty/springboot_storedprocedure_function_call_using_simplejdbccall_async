package com.sp.call.controller;

import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.sp.call.model.InitiateBuildRequest;
import com.sp.call.model.PortfolioResponse;
import com.sp.call.model.Request;
import com.sp.call.services.StoredProcedureCallService;

@RestController
public class StoredProcedureCallController {
	
	public static Logger logger = LoggerFactory.getLogger(StoredProcedureCallController.class);

	@Autowired
	StoredProcedureCallService service;

	@PostMapping("/spPiInitiateBuild")
	public PortfolioResponse spPiInitiateBuild(@RequestBody InitiateBuildRequest req) throws Exception {
		return service.spPiInitiateBuild(req);
	}
	
	
	@PostMapping("/triggerGroupAndStageCalls")
	public void triggerGroupAndStageCalls(@RequestBody Request req, @RequestHeader String token) throws Exception {
		logger.debug("@@@ Started triggerGroupAndStageCalls");
		 long startTime = System.currentTimeMillis();
		 service.triggerAnotherProcedures(req,token);
		 logger.debug("@@@ Time taken for Async call to execute triggerGroupAndStageCalls " + ((System.currentTimeMillis())- startTime));
	}
	
	
	
	
	
	
	
	/*@PostMapping("/spPiGroupProductsMain")
	public void spPiGroupProductsMain(@RequestBody Request req) throws Exception {
		 logger.debug("@@@ Started spPiGroupProductsMain");
		 long startTime = System.currentTimeMillis();
		 Future<Integer> sym_interaction_id = service.spPiGroupProductsMain(req);
		 logger.debug("@@@ Time taken for Async call to execute spPiGroupProductsMain " + ((System.currentTimeMillis())- startTime));
	}
	
	
	@PostMapping("/spPiStagePortfolioMain")
	public void spPiStagePortfolioMain(@RequestBody Request req) throws Exception {
		logger.debug("@@@ Started spPiStagePortfolioMain");
		 long startTime = System.currentTimeMillis();
		Future<Integer> sym_interaction_id = service.spPiStagePortfolioMain(req);
		logger.debug("@@@ Time taken for Async call to execute spPiStagePortfolioMain " + ((System.currentTimeMillis())- startTime));
	}*/
	
}
