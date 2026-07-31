// ❌ Boot 3 style — TestRestTemplate is less fluent
@SpringBootTest(webEnvironment = RANDOM_PORT)
class UserApiIT {
    @Autowired TestRestTemplate http;

    @Test
    void getUser() {
        ResponseEntity<User> res =
            http.getForEntity("/users/1", User.class);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(res.getBody().email()).isEqualTo("a@example.com");
    }
}

// ✅ Boot 4 — RestTestClient (aligned with RestClient)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
class UserApiIT {
    @Autowired RestTestClient client;

    @Test
    void getUser() {
        client.get()
            .uri("/users/{id}", 1)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.email").isEqualTo("a@example.com");
    }
}
