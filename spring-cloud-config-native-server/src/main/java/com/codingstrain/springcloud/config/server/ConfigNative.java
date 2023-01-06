package com.codingstrain.springcloud.config.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class ConfigNative {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigNative.class).run(args);
	}


}
