package com.codingstrain.springcloud.sample.libraryapp.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableFeignClients
public class AppMain {

	public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
	}

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .filter(new ServletBearerExchangeFilterFunction())
            .build();
    }
}
