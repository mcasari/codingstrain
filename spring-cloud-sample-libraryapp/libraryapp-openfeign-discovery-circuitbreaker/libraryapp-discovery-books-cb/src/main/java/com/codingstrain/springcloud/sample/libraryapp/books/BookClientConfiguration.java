package com.codingstrain.springcloud.sample.libraryapp.books;

import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;

@Configuration
public class BookClientConfiguration {

    @Bean
    public Contract feignContract() {
        return new SpringMvcContract();
    }
}
