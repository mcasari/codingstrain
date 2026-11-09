// ❌ Fake client in main — risk of shipping to prod
@Configuration
class MailConfig {
    @Bean
    MailClient mailClient() {
        return new NoOpMailClient(); // oops, production too
    }
}

// ✅ Test-only bean — never component-scanned from main
@SpringBootTest
class OrderServiceTest {
    @TestConfiguration
    static class MailTestConfig {
        @Bean
        MailClient mailClient() {
            return new NoOpMailClient();
        }
    }

    @Test
    void placesOrder() { /* … */ }
}
