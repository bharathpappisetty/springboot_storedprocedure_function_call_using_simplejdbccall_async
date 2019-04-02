package com.sp.call.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class Beans {

	public static Logger logger = LoggerFactory.getLogger(Beans.class);

	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

//		if (sslOffOn != null && sslOffOn.equals("off")) {
			logger.warn("## Getting httpclient by skipping SSL verfication");
				TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

				SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
				        .loadTrustMaterial(null, acceptingTrustStrategy)
				        .build();

				SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

				CloseableHttpClient httpClient = HttpClients.custom()
				        .setSSLSocketFactory(csf)
				        .build();

				HttpComponentsClientHttpRequestFactory requestFactory =
				        new HttpComponentsClientHttpRequestFactory();

				requestFactory.setHttpClient(httpClient);

				RestTemplate restTemplate = new RestTemplate(requestFactory);
				return restTemplate;
			}


}
