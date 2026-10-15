// ❌ Recalculates on every call — slow for heavy work
double score(String userId) {
    return loadAndRank(userId); // DB + ranking every time
}

// ✅ Thread-safe cache — compute once per key
class ScoreCache {
    private final ConcurrentMap<String, Double> cache = new ConcurrentHashMap<>();
    double score(String userId) {
        return cache.computeIfAbsent(userId, this::loadAndRank);
    }
    private double loadAndRank(String userId) {
        return /* expensive work */ 0.0;
    }
}
