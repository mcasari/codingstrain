// ── 1) Eureka Server (registry) ──────────────────────
@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApp {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerApp.class, args);
    }
}

// ── 2) Client microservice ─────────────────────────────
// application.yml
//   spring.application.name: books-service

// ── 3) Call another service by name (no fixed IP) ─────
@FeignClient(name = "author-service")
public interface AuthorClient {
    @GetMapping("/authors/{id}")
    Author getAuthor(@PathVariable String id);
}
