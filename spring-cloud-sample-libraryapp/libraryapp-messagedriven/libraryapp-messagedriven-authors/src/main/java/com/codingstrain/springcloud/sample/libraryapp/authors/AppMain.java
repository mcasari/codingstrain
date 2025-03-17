package com.codingstrain.springcloud.sample.libraryapp.authors;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppMain {

    @Autowired
    private ServerProperties serverProperties;

	public static void main(String[] args) {
        new SpringApplicationBuilder(AppMain.class).run(args);
	}

    @Bean
    public Consumer<String> receiveBookInfo() {
        return payload -> {
            System.out.println(payload);
            System.out.println("SERVER PORT: " + serverProperties.getPort());
        };
    }

    @Bean
    public Consumer<String> receiveBookInfoGroup() {
        return payload -> {
            System.out.println(payload);
            System.out.println("SERVER PORT: " + serverProperties.getPort());
        };
    }

    @Bean
    public Consumer<String> receiveBookInfoPartitioned() {
        return payload -> {
            System.out.println(payload);
            System.out.println("SERVER PORT: " + serverProperties.getPort());
        };
    }

}
