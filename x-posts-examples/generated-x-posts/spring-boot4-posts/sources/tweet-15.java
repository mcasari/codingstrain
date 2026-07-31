// In a shared library module:
@ConfigurationPropertiesSource
public record RetrySettings(int maxAttempts, Duration backoff) {}

// In the app module:
@ConfigurationProperties(prefix = "app.http")
public record HttpClientProperties(
    String baseUrl,
    RetrySettings retry  // sourced from the other module
) {}

# application.yml
app:
  http:
    base-url: https://api.example.com
    retry:
      max-attempts: 3
      backoff: 200ms
