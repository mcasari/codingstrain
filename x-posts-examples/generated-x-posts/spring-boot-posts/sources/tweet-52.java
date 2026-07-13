// ── CONSUMER (books-service): declare what you expect from author-service ──
@ExtendWith(PactConsumerTestExt.class)
class AuthorClientContractTest {

    @Pact(consumer = "books-service", provider = "author-service")
    V4Pact getAuthor(PactDslWithProvider builder) {
        return builder.given("author Goethe exists")
            .uponReceiving("GET author by name")
                .path("/authors/findAuthor").query("name=Goethe").method("GET")
            .willRespondWith()
                .status(200)
                .body("{\"name\":\"Goethe\",\"biography\":\"Bio info\"}")
            .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getAuthor")        // runs against a mock provider
    void callsAuthorService(MockServer mock) {
        String body = new RestTemplate().getForObject(
            mock.getUrl() + "/authors/findAuthor?name=Goethe", String.class);
        assertEquals("{\"name\":\"Goethe\",\"biography\":\"Bio info\"}", body);
    }
}

// ── PROVIDER (author-service): prove the real service honours the contract ──
@Provider("author-service")
@SpringBootTest(webEnvironment = RANDOM_PORT)
@PactBroker(url = "http://localhost:9292")
class AuthorProviderPactTest {

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyContracts(PactVerificationContext ctx) {
        ctx.verifyInteraction();                  // replay contract on the real app
    }
}
