package com.example.demo.listener;

import com.example.demo.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.event.TransactionPhase;

@Component
public class OrderEventListener {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreated(OrderCreatedEvent event) {

        System.out.println("Transaction committed. Sending confirmation for order: "
                + event.getOrderId());

        // simulate email or message publishing
    }
}