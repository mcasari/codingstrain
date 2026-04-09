package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner run(UserRepository repo, UserService service) {
        return args -> {
            // Generate test data
            for (int i = 1; i <= 10000; i++) {
                repo.save(new User("User_" + i));
            }

            System.out.println("=== DATA GENERATED ===");

            // Process using streaming
            service.processUsers();
        };
    }
}
