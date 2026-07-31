@Service
public class PaymentService {

    @Timed(value = "payments.process",
           extraTags = { "region", "eu" })
    @MeterTag(key = "method", value = "#payment.method()")
    public Receipt charge(Payment payment) {
        return gateway.charge(payment);
    }
}

// Resulting timer tags include method=CARD|WIRE|…
// Avoid tagging raw user IDs (high cardinality)
