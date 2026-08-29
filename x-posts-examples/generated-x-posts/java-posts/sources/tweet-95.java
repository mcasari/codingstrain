// ❌ God class — validation, persistence, email in one place
class OrderService {
    void placeOrder(Order order) {
        if (order.total() <= 0) throw new IllegalArgumentException();
        jdbc.update("insert into orders ...", order.id());
        mailer.send(order.email(), "Order confirmed");
    }
}

// ✅ Focused helpers + thin orchestrator
class OrderValidator {
    void validate(Order order) { /* one job */ }
}
class OrderRepository {
    void save(Order order) { /* one job */ }
}
class OrderNotifier {
    void sendConfirmation(Order order) { /* one job */ }
}

class OrderService {
    private final OrderValidator validator;
    private final OrderRepository repository;
    private final OrderNotifier notifier;

    void placeOrder(Order order) {
        validator.validate(order);
        repository.save(order);
        notifier.sendConfirmation(order);
    }
}
