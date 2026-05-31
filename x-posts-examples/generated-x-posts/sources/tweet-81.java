// ❌ Unused imports and dead variables — noise on every read
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.math.BigDecimal;

public BigDecimal total(List<Order> orders) {
    int unused = 42;
    String debugLabel = "temp";
    Order first = orders.isEmpty() ? null : orders.get(0);  // never used
    return orders.stream()
        .map(Order::amount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
}

// ✅ Keep only what the method actually uses
import java.math.BigDecimal;
import java.util.List;

public BigDecimal total(List<Order> orders) {
    return orders.stream()
        .map(Order::amount)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
}

// IDE: Optimize Imports (Ctrl+Alt+O / ⌃⌥O) · enable "unused" warnings in the compiler
