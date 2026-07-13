// ❌ Many constructor args — which boolean is which?
Order order = new Order("Mario", "Laptop", 2, true, "Leave at door");

// ✅ Builder — named fields, defaults, validate once at build()
Order order = Order.builder()
    .customer("Mario")
    .product("Laptop")
    .quantity(2)
    .express(true)
    .note("Leave at door")
    .build();

public final class Order {
    private final String customer, product, note;
    private final int quantity;
    private final boolean express;

    private Order(Builder b) {
        this.customer = Objects.requireNonNull(b.customer);
        this.product = Objects.requireNonNull(b.product);
        this.quantity = b.quantity;
        this.express = b.express;
        this.note = b.note;
    }

    static Builder builder() { return new Builder(); }

    static final class Builder {
        String customer, product, note;
        int quantity = 1;
        boolean express;
        Builder customer(String v) { customer = v; return this; }
        Builder product(String v)  { product = v;  return this; }
        Builder quantity(int v)    { quantity = v; return this; }
        Builder express(boolean v) { express = v;  return this; }
        Builder note(String v)     { note = v;     return this; }
        Order build() { return new Order(this); }
    }
}
