WebClient client = WebClient.create("https://api.example.com");

Mono<User> user = client.get()
    .uri("/users/{id}", id)
    .retrieve()
    .bodyToMono(User.class);
