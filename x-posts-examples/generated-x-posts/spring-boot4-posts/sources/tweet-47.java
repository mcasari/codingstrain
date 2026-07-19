// ❌ Fragile — relied on public auto-config types
// @Import(RedisAutoConfiguration.class)

// ✅ Depend on the starter; let Boot load auto-config
// spring-boot-starter-data-redis

// Custom auto-config for your library:
// META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
com.example.acme.AcmeAutoConfiguration

@AutoConfiguration
@ConditionalOnClass(AcmeClient.class)
public class AcmeAutoConfiguration {
    @Bean
    AcmeClient acmeClient(AcmeProperties props) {
        return new AcmeClient(props.baseUrl());
    }
}
