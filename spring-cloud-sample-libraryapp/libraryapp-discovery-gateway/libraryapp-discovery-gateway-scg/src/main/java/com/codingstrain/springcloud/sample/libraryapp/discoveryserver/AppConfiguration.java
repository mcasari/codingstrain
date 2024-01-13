package com.codingstrain.springcloud.sample.libraryapp.discoveryserver;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
class AppConfiguration {

    @Bean
    public RouterFunction<ServerResponse> gatewayRouterFunctionsAddReqHeader() {
        return GatewayRouterFunctions.route("routeDefinition")
            .route(RequestPredicates.GET("/red/{segment}"), HandlerFunctions.http("http://book-service:8082/library/getAuthor"))
            .before(BeforeFilterFunctions.addRequestHeader("X-Request-red", "blue-{segment}"))
            .build();
    }


}