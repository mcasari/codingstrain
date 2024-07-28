package com.codingstrain.springcloud.sample.libraryapp.gatewayserver;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class PostCustomFilterFactory extends AbstractGatewayFilterFactory<PostCustomFilterFactory.Configuration> {

    public PostCustomFilterFactory() {
        super(Configuration.class);
    }

    @Override
    public GatewayFilter apply(Configuration config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                    //Change the response
            }));
        };
    }

    public static class Configuration {
        //implement the configuration properties
    }

}