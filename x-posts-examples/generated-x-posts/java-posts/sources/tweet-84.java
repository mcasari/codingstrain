// ❌ One long method — validation, pricing, persistence, email all mixed
public void processOrder(Order order) {
    if (order == null || order.items().isEmpty()) {
        throw new IllegalArgumentException("empty order");
    }
    Customer customer = customerRepo.findById(order.customerId())
        .orElseThrow(() -> new NotFoundException(order.customerId()));
    BigDecimal total = order.items().stream()
        .map(i -> i.price().multiply(BigDecimal.valueOf(i.qty())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    if (total.compareTo(customer.creditLimit()) > 0) {
        throw new IllegalStateException("over credit limit");
    }
    Invoice invoice = new Invoice(customer.id(), order.items(), total);
    invoiceRepo.save(invoice);
    mailer.send(customer.email(), "Invoice " + invoice.id() + " ready");
}

// ✅ Extract helpers — each step has a name; processOrder reads like a summary
public void processOrder(Order order) {
    validate(order);
    Customer customer = loadCustomer(order.customerId());
    Invoice invoice = buildInvoice(order, customer);
    persist(invoice);
    notify(customer, invoice);
}
