package com.codingstrain.springcloud.discovery.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;


@SpringBootApplication
@EnableConfigServer
public class AppMain {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AppMain.class).run(args);
	}


}
