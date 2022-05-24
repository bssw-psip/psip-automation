package com.cefriel.coneyapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.ForwardedHeaderFilter;


@ComponentScan(basePackages = { "com.cefriel.coneyapi.service" })
@Configuration
@EnableTransactionManagement
//@EnableSwagger2
@EnableNeo4jRepositories(basePackages = "com.cefriel.coneyapi.repository")
public class ApplicationConfig {

	public static final String NEO4J_URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://neo4j:neo4j@localhost:7474";
	public static final String RETE_PATH = System.getenv("RETE_PATH") != null ? System.getenv("RETE_PATH") : "/var/lib/tomcat/webapps/coney-retejs-files/";
	public static final boolean SWAGGER_ENABLE = System.getenv("SWAGGER_ENABLE") != null && Boolean.parseBoolean(System.getenv("SWAGGER_ENABLE"));;
	
    @Value("${spring.profiles.active:}")
    private String activeSpringProfile;
    
    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
       return new ForwardedHeaderFilter();
    }
    
//	@Bean
//	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//			.host("rateyourproject.org")
//			.select()
//			.apis(RequestHandlerSelectors.any())
//			.paths(PathSelectors.any())
//			.build()
//			.apiInfo(apiInfo())
//			.pathMapping(activeSpringProfile.contains("local") ? "" : "/")
//			.enable(SWAGGER_ENABLE);
//	}

//	private ApiInfo apiInfo() {
//		ApiInfo apiInfo = new ApiInfo("RYP API", 
//					"RYP API",
//					"1.0.0", 
//					"https://github.com/bssw-psip/psip-automation/blob/next/api/LICENSE",
//					new Contact("", "", "g.watson@computer.org"), 
//					"Apache License, Version 2.0",
//					"http://www.apache.org/licenses/LICENSE-2.0", 
//					Collections.emptyList());
//		return apiInfo;
//	}
}
