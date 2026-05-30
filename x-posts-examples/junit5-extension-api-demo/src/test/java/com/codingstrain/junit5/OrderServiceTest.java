package com.codingstrain.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * No inheritance, no Runner: the test just declares the extensions it wants.
 * {@link LoggingExtension} and {@link TimingExtension} compose, and the
 * {@link Stopwatch} parameter is injected by {@link TimingExtension}'s
 * {@code ParameterResolver}.
 */
@ExtendWith({LoggingExtension.class, TimingExtension.class})
class OrderServiceTest {

    private final OrderService orders = new OrderService();

    @Test
    void placesOrder(Stopwatch stopwatch) {
        String result = orders.placeOrder("book-42");

        assertEquals("ORDER:book-42", result);
        assertTrue(stopwatch.elapsedMillis() >= 0, "elapsed time should be non-negative");
        System.out.println("elapsed = " + stopwatch.elapsedMillis() + "ms");
    }

    @Test
    void rejectsBlankItem(Stopwatch stopwatch) {
        org.junit.jupiter.api.Assertions.assertThrows(
            IllegalArgumentException.class, () -> orders.placeOrder("  "));
        assertTrue(stopwatch.elapsedMillis() >= 0);
    }
}
