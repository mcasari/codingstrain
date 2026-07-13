// ── 1) Config Server: one place serves every service's config ──
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigServerApp.class).run(args);
    }
}

// ── 2) Server application.yaml (serve files from the classpath) ──
// spring.profiles.active: native        # local files, no git needed
// spring.cloud.config.server.native.search-locations: classpath:/config
// server.port: 8888

// ── 3) /config/client-service.yaml (config for "client-service") ──
// myproperty: value

// ── 4) Client: import config from the server at startup ──
// spring.application.name: client-service
// spring.config.import: optional:configserver:http://localhost:8888

@Component
public class GreetingClient {
    @Value("${myproperty}")               // resolved from the Config Server
    private String myProperty;
}
