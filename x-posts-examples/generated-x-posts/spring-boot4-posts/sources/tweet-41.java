@HttpExchange("https://billing.internal")
public interface BillingClient {
    @GetExchange("/invoices/{id}")
    Invoice get(@PathVariable String id);
}

@Service
public class BillingFacade {
    private final BillingClient declarative;
    private final RestClient adhoc;

    public BillingFacade(BillingClient declarative, RestClient.Builder b) {
        this.declarative = declarative;
        this.adhoc = b.baseUrl("https://billing.internal").build();
    }
}
