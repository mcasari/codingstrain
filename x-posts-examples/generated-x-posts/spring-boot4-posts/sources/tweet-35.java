# application.yml
spring:
  data:
    mongodb:
      representation:
        big-decimal: decimal128

@Document
public class Invoice {
    @Id String id;
    BigDecimal total; // stored as Decimal128
}

// Alternatives depend on Boot’s enum values —
// set explicitly so prod/stage match.
