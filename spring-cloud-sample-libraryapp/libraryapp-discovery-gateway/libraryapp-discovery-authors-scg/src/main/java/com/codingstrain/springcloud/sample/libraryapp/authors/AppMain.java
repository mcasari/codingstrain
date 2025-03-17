package com.codingstrain.springcloud.sample.libraryapp.authors;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
@EntityScan(basePackages = { "com.codingstrain.springcloud.sample.libraryapp.model" })
public class AppMain {

	public static void main(String[] args) {
        new SpringApplicationBuilder(AppMain.class).run(args);
	}


}
