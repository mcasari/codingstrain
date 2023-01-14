package com.codingstrain.springcloud.config.client;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
public class ConfigClient {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigClient.class).run(args);
	}


}
