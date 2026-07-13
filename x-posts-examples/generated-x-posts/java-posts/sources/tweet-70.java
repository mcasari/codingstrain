// ❌ Public fields — callers can break invariants directly
public class BankAccount {
    public BigDecimal balance = BigDecimal.ZERO;
}

BankAccount acc = new BankAccount();
acc.balance = new BigDecimal("-100");   // no validation, no control

// ✅ Private field + accessors — you decide what is allowed
public class BankAccount {
    private BigDecimal balance = BigDecimal.ZERO;

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(BigDecimal amount) {
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("insufficient funds");
        }
        balance = balance.subtract(amount);
    }
}
