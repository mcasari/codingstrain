// ❌ Raw millis — easy to mix units and get wrong math
long start = System.currentTimeMillis();
doWork();
long elapsedMs = System.currentTimeMillis() - start;

// ✅ Instant + Duration — clear intent
Instant start = Instant.now();
doWork();
Duration elapsed = Duration.between(start, Instant.now());
long elapsedMs = elapsed.toMillis();
