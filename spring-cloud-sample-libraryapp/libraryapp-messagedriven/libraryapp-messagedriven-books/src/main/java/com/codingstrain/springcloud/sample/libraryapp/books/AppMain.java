package com.codingstrain.springcloud.sample.libraryapp.books;

import java.util.function.Consumer;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppMain {

	public static void main(String[] args) {
        new SpringApplicationBuilder(AppMain.class).run(args);
	}

    @Bean
    public Consumer<String> receive() {
        return payload -> {
            System.out.println(payload);
        };
    }


}
