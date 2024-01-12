package com.codingstrain.springcloud.sample.libraryapp.discoveryserver;

import java.time.ZonedDateTime;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
class AppConfiguration {

    @Bean
    public RouterFunction<ServerResponse> gatewayRouterFunctionsAddReqHeader() {
        return GatewayRouterFunctions.route("after_route").GET("/red", HandlerFunctions.http("https://example.org"))
                .before(BeforeFilterFunctions.addRequestHeader("X-Request-red", "blue")).build();
    }

    @Bean
    public RouterFunction<ServerResponse> gatewayRouterFunctionsAfter() {
        return GatewayRouterFunctions.route("after_route")
            .route(GatewayRequestPredicates.after(ZonedDateTime.parse("2017-01-20T17:42:47.789-07:00[America/Denver]")), HandlerFunctions.http(
                "https://example.org"))
            .build();
    }
}