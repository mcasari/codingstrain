// ❌ Mutable state shared across threads — races, torn reads
public class Balance {
    private BigDecimal amount = BigDecimal.ZERO;
    public void deposit(BigDecimal v) { amount = amount.add(v); }
    public BigDecimal get() { return amount; }
}
// Two threads calling deposit() → lost updates

// ✅ Immutable object — update by creating a new instance
public record Balance(BigDecimal amount) {
    public Balance deposit(BigDecimal v) {
        return new Balance(amount.add(v));
    }
}
// Threads share Balance snapshots; each deposit returns a new one

// ✅ Immutable collections — safe to publish without locking
List<String> roles = List.of("READ", "WRITE");
Map<String, Integer> limits = Map.of("free", 10, "pro", 100);

// Share a defensive copy if the source is still mutable
List<String> snapshot = List.copyOf(mutableRoles);
