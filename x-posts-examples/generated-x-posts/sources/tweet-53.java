// ── Feign client + circuit breaker on remote call ───
@FeignClient(name = "author-service")
public interface AuthorClient {

    @GetMapping("/authors/getInstance")
    @CircuitBreaker(name = "CircuitBreakerService")
    String getInstance();
}

// ── Controller with fallback when circuit is OPEN ───
@RestController
public class BookController {

    @GetMapping("/getAuthorServiceInstance")
    @CircuitBreaker(name = "CircuitBreakerApi",
            fallbackMethod = "getAuthorServiceInstanceFallback")
    public String getAuthorServiceInstance() {
        return bookService.getAuthorServiceInstance();
    }

    public String getAuthorServiceInstanceFallback(Exception ex) {
        return "Fallback content";
    }
}

// ── application.yml (Resilience4j) ───────────────────
// resilience4j.circuitbreaker.instances.CircuitBreakerApi:
//   failure-rate-threshold: 50
//   wait-duration-in-open-state: 5s
