package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(UserRepository repository) {
        return args -> {
            for (int i = 1; i <= 100; i++) {
                repository.save(new User("User " + i));
            }
        };
    }
}