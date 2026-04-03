package com.example.demo;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MockResetTest {


    @Autowired
    private MyController controller;

    @MockBean
    private MyService myService;

    @Test
    @Order(1)
    void testA() {
        when(myService.getData()).thenReturn("A");
        System.out.println(controller.fetch()); // A
    }

    @Test
    @Order(2)
    void testB() {
        System.out.println(controller.fetch()); // should be A if leakage happens
    }
}
