# application.yml — enable virtual threads
spring:
  threads:
    virtual:
      enabled: true

@Configuration
class OutboundApiConfig {
    @Bean
    RestClient api(RestClient.Builder builder) {
        return builder.baseUrl("https://api.example.com").build();
    }
}

@RestController
class UserController {
    private final RestClient api;
    UserController(RestClient api) { this.api = api; }

    @GetMapping("/users/{id}")
    User user(@PathVariable Long id) {
        return api.get()
                  .uri("/users/{id}", id)
                  .retrieve()
                  .body(User.class);
    }
}
// JDK HttpClient blocks on virtual threads — cheap concurrent I/O
