// ❌ Field injection — dependency hidden, hard to test without Spring
@RestController
class OrderController {

    @Autowired
    private OrderService orders;

    @GetMapping("/orders")
    List<Order> list() { return orders.findAll(); }
}

// ✅ Constructor injection — explicit, final, easy to unit-test
@RestController
class OrderController {

    private final OrderService orders;

    OrderController(OrderService orders) {
        this.orders = orders;
    }

    @GetMapping("/orders")
    List<Order> list() { return orders.findAll(); }
}
