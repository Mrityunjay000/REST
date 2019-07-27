package com.in28minutes.rest.webservices.restfulwebservices;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays.*;

import org.assertj.core.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2  
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT = 
			new Contact("Ranga Karnam", "http://www.in28minutes.com", "in28minutes@gmail.com");
	  public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
			  "Awesome API Title", "Awesome API Documentation", "1.0", "urn:tos",
	          DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");
	  
	//private static String[] array = {"application/json", "application/xml"};  
	//private static Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json", "application/xml"));
	//this should have worked but it did not look into it when u have the time. 
	


	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(DEFAULT_API_INFO); //used to configure the basic information section of the swagger thing.
				//.produces(DEFAULT_PRODUCES_AND_CONSUMES) //these would have changed the produces and consumes info on the swagger thing. 
				//.consumes(DEFAULT_PRODUCES_AND_CONSUMES);  
	}
}
