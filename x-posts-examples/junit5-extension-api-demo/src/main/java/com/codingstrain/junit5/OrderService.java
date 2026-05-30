package com.codingstrain.junit5;

/** Trivial system under test for the JUnit 5 extension demo. */
public class OrderService {

    public String placeOrder(String item) {
        if (item == null || item.isBlank()) {
            throw new IllegalArgumentException("item must not be blank");
        }
        return "ORDER:" + item;
    }
}
