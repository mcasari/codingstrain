// Mental model for Boot 4 upgrades:
// 1) Java 17+ (21/25 recommended)
// 2) spring-boot-starter-web → webmvc
// 3) Jackson 3 (JsonMapper) / tools.jackson.*
// 4) @MockBean → @MockitoBean
// 5) Add tech starters (flyway, restclient, otel, …)
// 6) Run with spring-boot-properties-migrator
// 7) Replace TestRestTemplate with RestTestClient

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
