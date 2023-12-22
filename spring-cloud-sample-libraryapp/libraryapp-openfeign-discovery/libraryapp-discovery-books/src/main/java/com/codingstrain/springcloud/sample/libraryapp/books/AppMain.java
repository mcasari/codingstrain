package com.codingstrain.springcloud.sample.libraryapp.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.codingstrain.springcloud.sample.libraryapp.books.client.AuthorClient;
import com.codingstrain.springcloud.sample.libraryapp.books.client.ReviewClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(defaultConfiguration = BookClientConfiguration.class, clients = { AuthorClient.class, ReviewClient.class })
public class AppMain {

	public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
	}


}
