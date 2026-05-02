package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql("/test-data.sql")
class UserRepositoryTest {

    @Autowired
    UserRepository repo;

    @Test
    void testUserLoadedFromSql() {
        var user = repo.findById(1L).orElseThrow();
        assertEquals("Alice", user.getName());
    }
}
