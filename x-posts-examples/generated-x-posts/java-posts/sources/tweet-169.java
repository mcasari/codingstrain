// ❌ One exclusive lock — readers block each other
synchronized String getSlow(String key) {
    return cache.get(key);
}
synchronized void putSlow(String key, String value) {
    cache.put(key, value);
}

// ✅ ReadWriteLock — many readers, exclusive writer
private final ReentrantReadWriteLock rw =
    new ReentrantReadWriteLock();
String get(String key) {
    rw.readLock().lock();
    try { return cache.get(key); }
    finally { rw.readLock().unlock(); }
}
void put(String key, String value) {
    rw.writeLock().lock();
    try { cache.put(key, value); }
    finally { rw.writeLock().unlock(); }
}
