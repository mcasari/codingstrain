package com.codingstrain.springcloud.sample.libraryapp.review;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("mygroup")
				.apiInfo(apiInfo()).select().paths( regex("/library/.*")).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("REST API example")
				.description("A minimal REST example with H2 in memory db as persistence layer")
				.termsOfServiceUrl("http://codingstrain.com")
				.license("REST API example License")
				.licenseUrl("fake@gmail.com")
				.version("1.0")
				.build();
	}

}