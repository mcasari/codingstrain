package com.example.demo.service;

import com.example.demo.entity.Order;
import com.example.demo.event.OrderCreatedEvent;
import com.example.demo.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ApplicationEventPublisher publisher;

    public OrderService(OrderRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public Order createOrder(String product) {

        Order order = new Order(product);
        repository.save(order);

        publisher.publishEvent(new OrderCreatedEvent(order.getId()));

        return order;
    }
}