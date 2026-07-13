@Transactional
public Order createOrder(String product) {
    Order order = repository.save(new Order(product));
    publisher.publishEvent(new OrderCreatedEvent(order.getId()));
    return order;
}
