// ❌ Vague names — you must read the body to know what they do
public List<User> get(boolean b) { ... }
public void process(Order o) { ... }
public BigDecimal calc(Cart c) { ... }

// ✅ Names that reveal intent — the call site reads like a sentence
public List<User> findActiveUsers() { ... }
public void cancelOrder(Order order) { ... }
public BigDecimal calculateCartTotal(Cart cart) { ... }

List<User> active = findActiveUsers();
BigDecimal total = calculateCartTotal(cart);
