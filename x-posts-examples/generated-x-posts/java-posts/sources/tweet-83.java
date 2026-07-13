// ❌ Deep nesting — hard to follow, happy path buried at the bottom
public void shipOrder(User user, Order order) {
    if (user != null) {
        if (user.isActive()) {
            if (order != null) {
                if (order.isPaid()) {
                    warehouse.ship(order);
                } else {
                    throw new IllegalStateException("order not paid");
                }
            } else {
                throw new IllegalArgumentException("order required");
            }
        } else {
            throw new IllegalStateException("user inactive");
        }
    } else {
        throw new IllegalArgumentException("user required");
    }
}

// ✅ Guard clauses — fail fast, main logic stays flat
public void shipOrder(User user, Order order) {
    if (user == null) throw new IllegalArgumentException("user required");
    if (!user.isActive()) throw new IllegalStateException("user inactive");
    if (order == null) throw new IllegalArgumentException("order required");
    if (!order.isPaid()) throw new IllegalStateException("order not paid");

    warehouse.ship(order);
}
