@ConfigurationProperties(prefix = "app")
public record AppProps(String name, int timeout) {}

// Binds app.name / app.timeout from application.yaml
