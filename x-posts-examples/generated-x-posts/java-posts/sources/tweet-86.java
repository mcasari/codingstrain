// ❌ Mutable static state — shared globally, tests interfere with each other
public class Config {
    private static String configPath = "/etc/app/config.yaml";

    public static String getConfigPath() { return configPath; }
    public static void setConfigPath(String path) { configPath = path; }
}

// test A: Config.setConfigPath("/tmp/a") ...
// test B: still sees "/tmp/a" — flaky, hard to isolate

public class ReportService {
    public String load() {
        return Files.readString(Path.of(Config.getConfigPath()));
    }
}

// ✅ Inject configuration — each caller/test owns its instance
public class Config {
    private final Path configPath;
    public Config(Path configPath) { this.configPath = configPath; }
    public Path configPath() { return configPath; }
}

public class ReportService {
    private final Config config;
    public ReportService(Config config) { this.config = config; }

    public String load() {
        return Files.readString(config.configPath());
    }
}

// static is fine for true constants: private static final int MAX_RETRY = 3;
