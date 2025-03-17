package com.codingstrain.springcloud.discovery.gateway;

import org.springframework.web.reactive.function.client.WebClient;

//@Configuration
//@LoadBalancerClient(value = "stores", configuration = CustomLoadBalancerConfiguration.class)
public class MyConfiguration {

//    @Bean
//    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}