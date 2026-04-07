package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id) {
        if (id == 1) {
            return "User found";
        }
        throw new RuntimeException("Database connection failed!");
    }
}
