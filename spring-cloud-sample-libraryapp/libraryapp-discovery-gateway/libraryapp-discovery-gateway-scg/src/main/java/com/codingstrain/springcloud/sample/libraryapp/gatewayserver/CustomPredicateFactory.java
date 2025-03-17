package com.codingstrain.springcloud.sample.libraryapp.gatewayserver;

import java.util.function.Predicate;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class CustomPredicateFactory extends AbstractRoutePredicateFactory<CustomPredicateFactory.Configuration> {

    public CustomPredicateFactory() {
        super(Configuration.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(Configuration config) {
        return exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            //compare request with custom properties
            return matches(config, request);
        };
    }

    private boolean matches(Configuration config, ServerHttpRequest request) {
        //implement matching logic
        return false;
    }

    public static class Configuration {
        //implement custom configuration properties
    }

}