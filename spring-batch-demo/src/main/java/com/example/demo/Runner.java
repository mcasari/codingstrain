package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Runner {

    @Bean
    CommandLineRunner run(BatchService service) {
        return args -> {
            service.insertUsers(1000);
            System.out.println("Inserted 1000 users with batching");
        };
    }
}
