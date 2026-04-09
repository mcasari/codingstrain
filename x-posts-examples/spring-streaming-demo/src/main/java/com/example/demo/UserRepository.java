package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u")
    Stream<User> streamAllUsers();
}
