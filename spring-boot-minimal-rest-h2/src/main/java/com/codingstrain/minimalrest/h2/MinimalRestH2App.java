package com.codingstrain.minimalrest.h2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class MinimalRestH2App {

	public static void main(String[] args) {
		SpringApplication.run(MinimalRestH2App.class, args);
	}


}
