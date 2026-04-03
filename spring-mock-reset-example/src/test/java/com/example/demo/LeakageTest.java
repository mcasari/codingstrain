package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class LeakageTest {

    @Autowired
    private MyController controller;

    @MockBean
    private MyService myService;

    @Test
    void testA_setsStub() {
        when(myService.getData()).thenReturn("A");
        assert controller.fetch().equals("A");
    }

    @Test
    void testB_leaksFromPreviousTest() {
        // This test will FAIL if mocks are not reset
        assert controller.fetch().equals("REAL");
    }
}
