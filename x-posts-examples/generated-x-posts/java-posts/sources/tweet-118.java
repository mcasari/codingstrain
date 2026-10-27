// ❌ Switch grows with every new status
double fee(String status, double amount) {
    switch (status) {
        case "NEW": return amount * 0.02;
        case "VIP": return amount * 0.01;
        case "CANCELLED": return 0;
        default: throw new IllegalArgumentException(status);
    }
}

// ✅ Polymorphism — each type owns its fee
interface Priced {
    double fee(double amount);
}
// NewOrder / VipOrder / CancelledOrder each implement Priced
double fee(Priced order, double amount) {
    return order.fee(amount); // no switch to edit
}
