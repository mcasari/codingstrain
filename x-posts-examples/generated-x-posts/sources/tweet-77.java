// ❌ catch (Exception) — bugs, OOM, and real failures all look the same
try {
    Invoice invoice = parser.parse(file);
    repository.save(invoice);
} catch (Exception e) {
    log.warn("Failed to import invoice", e);
    return;   // also swallows NullPointerException, OutOfMemoryError, ...
}

// ✅ Catch only what you can handle — let the rest propagate
try {
    Invoice invoice = parser.parse(file);
    repository.save(invoice);
} catch (IOException e) {
    log.warn("Cannot read {}", file, e);
} catch (JsonProcessingException e) {
    log.warn("Invalid JSON in {}", file, e);
} catch (DuplicateKeyException e) {
    log.info("Invoice already imported: {}", file);
}

// ❌ catch (Throwable) is worse — catches Errors you must not recover from
// catch (Throwable t) { ... }   // never do this
