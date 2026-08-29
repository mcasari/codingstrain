// ✅ Checked — external I/O failure; caller must handle
void loadConfig() throws IOException {
    Files.readString(Path.of("app.properties"));
}

try {
    loadConfig();
} catch (IOException e) {
    throw new UncheckedIOException(e);
}

// ✅ Unchecked — programming/validation error
void setDiscount(int percent) {
    if (percent < 0 || percent > 100) {
        throw new IllegalArgumentException("percent out of range");
    }
}
// no throws clause needed at call sites
