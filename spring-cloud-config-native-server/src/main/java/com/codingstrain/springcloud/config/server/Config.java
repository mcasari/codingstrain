package com.codingstrain.springcloud.config.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class Config {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Config.class).run(args);
	}


}
