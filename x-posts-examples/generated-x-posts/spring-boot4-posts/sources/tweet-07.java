@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureRestTestClient
class UserApiIT {

    @Autowired
    RestTestClient client;

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
