package com.codingstrain.springcloud.sample.libraryapp.authors;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    @Bean
    public Supplier<String> source() {
        return () -> {
            String message = "Supplier message";
            System.out.println("Sending value: " + message);
            return message;

        };
    }

    @Bean
    public Function<String, String> uppercase() {
        return payload -> {
            System.out.println(payload);
            return payload.toUpperCase();
        };
    }

}
