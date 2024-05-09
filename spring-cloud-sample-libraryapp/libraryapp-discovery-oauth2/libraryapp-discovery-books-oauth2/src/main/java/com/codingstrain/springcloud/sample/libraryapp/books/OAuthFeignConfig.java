package com.codingstrain.springcloud.sample.libraryapp.books;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

public class OAuthFeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        OAuthAccessTokenManager clientCredentialsFeignManager = new OAuthAccessTokenManager();
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + clientCredentialsFeignManager.getAccessToken());
        };
    }

}