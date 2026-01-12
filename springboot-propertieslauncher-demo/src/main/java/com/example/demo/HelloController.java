package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${demo.message:Hello from default config}")
    private String message;

    @GetMapping("/")
    public String hello() {
        return message;
    }
}
