package com.ssafy.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

//Swagger-UI 확인
//http://localhost/swagger-ui.html

//@OpenAPIDefinition(
//	    info = @Info(
//	        title = "SSAFY Board API 명세서",
//	        description = "<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 Board API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">",
//	        version = "v1",
//	        contact = @Contact(
//	            name = "hissam",
//	            email = "hissam@ssafy.com",
//	            url = "http://edu.ssafy.com"
//	        )
//	    )
//	)

@Configuration
public class SwaggerConfiguration {

	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("서울 16반 EnjoyTrip 방태연 & 정현우").description(
				"Swagger<img src=\"/assets/img/ssafy_logo.png\" width=\"150\">")
				.version("PJT6");
				//.contact(new io.swagger.v3.oas.models.info.Contact().name("")
				//	.email("hissam@ssafy.com").url("http://edu.ssafy.com"));

		return new OpenAPI().components(new Components()).info(info);
	}
	
	@Bean
	public GroupedOpenApi attractionApi() {
		return GroupedOpenApi.builder().group("ssafy-attraction").pathsToMatch("/**").build();
	}
//	
//	@Bean
//	public GroupedOpenApi boardApi() {
//		return GroupedOpenApi.builder().group("ssafy-board").pathsToMatch("/controller/Board*").build();
//	}
//	
//	@Bean
//	public GroupedOpenApi memberApi() {
//		return GroupedOpenApi.builder().group("ssafy-member").pathsToMatch("/controller/Memeber*").build();
//	}
//	
//	@Bean
//	public GroupedOpenApi recommandApi() {
//		return GroupedOpenApi.builder().group("ssafy-recommand").pathsToMatch("/controller/Recommand**").build();
//	}
	
//
//	@Bean
//	public GroupedOpenApi publicApi() {
//		return GroupedOpenApi.builder().group("ssafy-admin").pathsToMatch("/admin/**").build();
//	}
//
//	@Bean
//	public GroupedOpenApi adminApi() {
//		return GroupedOpenApi.builder().group("ssafy-user").pathsToMatch("/user/**").build();
//	}
//	
//	@Bean
//	public GroupedOpenApi fileApi() {
//		return GroupedOpenApi.builder().group("ssafy-file").pathsToMatch("/file/**").build();
//	}

}