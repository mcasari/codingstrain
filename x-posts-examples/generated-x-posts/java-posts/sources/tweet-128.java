// ❌ Platform thread — maps to a real OS thread
Thread t = new Thread(() -> http.get(url));
t.start(); // expensive if you create thousands

// ✅ Virtual thread — JVM-managed, cheap to start
Thread.ofVirtual()
    .name("fetch-", 0)
    .start(() -> http.get(url));

// Or one-liner:
Thread.startVirtualThread(() -> http.get(url));
