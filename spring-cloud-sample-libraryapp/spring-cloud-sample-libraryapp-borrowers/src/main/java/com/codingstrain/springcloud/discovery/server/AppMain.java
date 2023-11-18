package com.codingstrain.springcloud.discovery.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class AppMain {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppMain.class).run(args);
	}


}
