package com.codingstrain.springcloud.sample.libraryapp.gatewayserver;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class PreCustomFilterFactory extends AbstractGatewayFilterFactory<PreCustomFilterFactory.Configuration> {

    public PreCustomFilterFactory() {
        super(Configuration.class);
    }

    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            //use builder to modify the request
            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }

    public static class Configuration {
        //implement the configuration properties
    }

}