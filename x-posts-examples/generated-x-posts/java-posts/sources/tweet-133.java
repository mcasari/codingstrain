public Order(Customer customer) {
    // Fail fast at construction with a clear message
    this.customer = Objects.requireNonNull(customer, "customer");
}
