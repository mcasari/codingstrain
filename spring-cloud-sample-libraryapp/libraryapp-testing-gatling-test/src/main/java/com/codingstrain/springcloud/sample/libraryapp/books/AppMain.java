package com.codingstrain.springcloud.sample.libraryapp.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppMain {

	public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
	}


}
