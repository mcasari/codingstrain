package com.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository repository;

    @PersistenceContext
    private EntityManager em;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public void processUsers() {
        AtomicInteger counter = new AtomicInteger();

        try (Stream<User> stream = repository.streamAllUsers()) {
            stream.forEach(user -> {
                System.out.println(user.getName());

                if (counter.incrementAndGet() % 50 == 0) {
                    em.clear();
                }
            });
        }
    }
}
