package com.codingstrain.springcloud.discovery.gateway;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
public class AppMain {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppMain.class).run(args);
	}


}
