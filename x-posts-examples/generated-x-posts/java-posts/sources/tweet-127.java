// ❌ HashMap grows forever — nothing is evicted
Map<String, byte[]> cache = new HashMap<>();
cache.put(id, payload); // memory leak over time

// ✅ LinkedHashMap LRU — eldest entry removed when over capacity
class LruCache<K, V> extends LinkedHashMap<K, V> {
    private final int max;
    LruCache(int max) {
        super(16, 0.75f, true); // accessOrder = true
        this.max = max;
    }
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > max; // drop least-recently used
    }
}
