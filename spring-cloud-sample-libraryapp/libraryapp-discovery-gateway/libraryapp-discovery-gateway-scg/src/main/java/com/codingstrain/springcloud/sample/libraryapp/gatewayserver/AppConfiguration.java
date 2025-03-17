package com.codingstrain.springcloud.sample.libraryapp.gatewayserver;

import org.springframework.context.annotation.Configuration;

@Configuration
class AppConfiguration {

    //    @Bean
    //    public RouterFunction<ServerResponse> gatewayRouterFunctionsAddReqHeader() {
    //        return GatewayRouterFunctions.route("routeDefinition")
    //            .route(RequestPredicates.GET("/api"), HandlerFunctions.http("http://localhost:8090"))
    //            .before(BeforeFilterFunctions.addRequestHeader("X-Request-red", "blue"))
    //            .build();
    //    }


}