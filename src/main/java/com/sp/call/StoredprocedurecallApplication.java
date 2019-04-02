package com.sp.call;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@RestController
//@EnableSwagger2
@Configuration
public class StoredprocedurecallApplication extends WebMvcConfigurationSupport {

	public static void main(String[] args) {
		SpringApplication.run(StoredprocedurecallApplication.class, args);
	}
	
	
	/*
	@Bean
	public Docket swaggerApi() { 
		return new Docket(DocumentationType.SWAGGER_2) 
				.groupName("InitiateBuild-StoredProcedure-Calls")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.sp.call"))              
				.paths(PathSelectors.any())                          
				.build().apiInfo(new ApiInfoBuilder()
		                .title("Backend Service - stored procedure calls")
		                .description("This Service mainly does \n \t 1) Trigger SP_PI_INITIATE_BUILD procedure and get PORTFOLIO_ID & SYSTEM_INTERACTION_ID. "
		                		+ "\n \t 2) Make ASYNC call to trigger GROUP_PRODUCTS_MAIN & STAGE_PORTFOLIO_MAIN procedures."
		                		+ "\n \t 3) Get resultant JSON for creating new Portfolio , and sends POST request to SIGMA system")
		                .version("1.0").build());     
	}


	// Adding swagger UI resources to fix swagger UI issue. 
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");

	}
	*/

}
