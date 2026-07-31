# application.yml
spring:
  threads:
    virtual:
      enabled: true

@Bean
RestClient api(RestClient.Builder builder) {
    // Boot wires JDK HttpClient with virtual threads
    return builder.baseUrl("https://api.example.com").build();
}

@GetMapping("/proxy/{id}")
User proxy(@PathVariable Long id) {
    return api.get().uri("/users/{id}", id)
              .retrieve().body(User.class);
}
