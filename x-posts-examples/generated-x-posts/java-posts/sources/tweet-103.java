// ❌ Field injection — hidden deps, hard to unit-test
@RestController
class FieldInjectedOrders {
    @Autowired
    private OrderService orders;

    @GetMapping("/orders")
    List<Order> list() { return orders.findAll(); }
}

// ✅ Constructor injection — explicit, final, testable
@RestController
class ConstructorInjectedOrders {
    private final OrderService orders;

    ConstructorInjectedOrders(OrderService orders) {
        this.orders = orders;
    }

    @GetMapping("/orders")
    List<Order> list() { return orders.findAll(); }
}
