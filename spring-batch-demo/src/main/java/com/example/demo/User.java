package com.example.demo;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    public User() {}

    public User(String name) {
        this.name = name;
    }
}
