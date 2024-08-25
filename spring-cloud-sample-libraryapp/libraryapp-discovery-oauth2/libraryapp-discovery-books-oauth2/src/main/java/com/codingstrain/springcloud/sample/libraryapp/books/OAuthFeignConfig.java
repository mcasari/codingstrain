package com.codingstrain.springcloud.sample.libraryapp.books;

public class OAuthFeignConfig {

	//This configuration is needed to forward the access token from one service the other
	//In this sample we keep simple and consider only one service, so this is not required. It is just a remainder
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        OAuthAccessTokenManager clientCredentialsFeignManager = new OAuthAccessTokenManager();
//        return requestTemplate -> {
//            requestTemplate.header("Authorization", "Bearer " + clientCredentialsFeignManager.getAccessToken());
//        };
//    }

}