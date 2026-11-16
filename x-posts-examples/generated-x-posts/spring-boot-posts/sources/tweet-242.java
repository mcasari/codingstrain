// ❌ Properties bind with no checks — bugs show up at runtime
@ConfigurationProperties(prefix = "app.mail")
class MailProperties {
    String host;      // can be null / blank
    int port = -1;    // nonsense value still starts the app
}

// ✅ @Validated — invalid config fails startup
@Validated
@ConfigurationProperties(prefix = "app.mail")
class MailProperties {
    @NotBlank String host;
    @Min(1) @Max(65535) int port;
}
