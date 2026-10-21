// ❌ Boot 3 — old package + factories key
import org.springframework.boot.env.EnvironmentPostProcessor;
# META-INF/spring.factories
org.springframework.boot.env.EnvironmentPostProcessor=\
com.example.env.RegionEnvironmentPostProcessor

// ✅ Boot 4 — package is now org.springframework.boot
import org.springframework.boot.EnvironmentPostProcessor;
public class RegionEnvironmentPostProcessor
        implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(
            ConfigurableEnvironment env,
            SpringApplication application) {
        // add defaults before context refresh
    }
}
# META-INF/spring.factories
org.springframework.boot.EnvironmentPostProcessor=\
com.example.env.RegionEnvironmentPostProcessor
