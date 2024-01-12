package com.codingstrain.springcloud.sample.libraryapp.discoveryserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;



@SpringBootApplication
public class AppMain {

	public static void main(String[] args) {
        new SpringApplicationBuilder(AppMain.class).run(args);
	}

    @Bean
    public RouterFunction<ServerResponse> getRoute() {
        return GatewayRouterFunctions.route()
            .GET("/get", HandlerFunctions.http("https://httpbin.org"))
            .build();
    }


}
