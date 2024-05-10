package com.codingstrain.springcloud.sample.libraryapp.gatewayserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class AppMain {

	public static void main(String[] args) {
        new SpringApplicationBuilder(AppMain.class).run(args);
	}


}
