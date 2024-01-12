package com.codingstrain.springcloud.sample.libraryapp.review;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public class SwaggerConfig {

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("REST API example")
				.description("A minimal REST example")
				.termsOfServiceUrl("http://codingstrain.com")
				.license("REST API example License")
				.licenseUrl("fake@gmail.com")
				.version("1.0")
				.build();
	}

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("mygroup")
				.apiInfo(apiInfo()).select().paths( regex("/library/.*")).build();
	}

}