// ❌ New OS thread per job — costly, unbounded
for (String url : urls) {
    new Thread(() -> http.get(url)).start(); // no limit, hard to join
}

// ✅ Reuse a fixed pool — bounded and manageable
ExecutorService pool = Executors.newFixedThreadPool(8);
try {
    for (String url : urls) {
        pool.submit(() -> http.get(url));
    }
} finally {
    pool.shutdown(); // always stop the pool
}
