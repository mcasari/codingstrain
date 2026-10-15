// ❌ Platform thread pool — limited OS threads for blocking I/O
try (var exec = Executors.newFixedThreadPool(200)) {
    for (String url : urls) {
        exec.submit(() -> http.get(url)); // each blocks a real thread
    }
}

// ✅ Virtual thread per task — scale blocking calls cheaply
try (var exec = Executors.newVirtualThreadPerTaskExecutor()) {
    for (String url : urls) {
        exec.submit(() -> http.get(url)); // blocks without hogging OS threads
    }
}
