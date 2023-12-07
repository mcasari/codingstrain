package com.codingstrain.springcloud.sample.libraryapp.discoveryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class AppMain {

	public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
	}


}
