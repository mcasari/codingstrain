package com.example.demo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertUsers(int count) {
        for (int i = 0; i < count; i++) {
            entityManager.persist(new User("User " + i));

            if (i % 50 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }
}
