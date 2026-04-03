package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;

import static org.mockito.Mockito.*;

@SpringBootTest
class FixedTest {

    @Autowired
    private MyController controller;

    @MockBean(reset = MockReset.BEFORE)
    private MyService myService;

    @Test
    void testA_setsStub() {
        when(myService.getData()).thenReturn("A");
        assert controller.fetch().equals("A");
    }

    @Test
    void testB_noLeakage() {
        // Now works correctly
        assert controller.fetch().equals("REAL");
    }
}
