// ❌ Static cache grows forever — GC cannot reclaim entries
class SessionCache {
    private static final Map<String, byte[]> DATA = new HashMap<>();

    static void put(String id, byte[] payload) {
        DATA.put(id, payload); // never removed
    }
}

// ✅ Evict or remove when done — keep the map bounded
class SessionCache {
    private static final Map<String, byte[]> DATA = new HashMap<>();

    static void put(String id, byte[] payload) {
        DATA.put(id, payload);
    }

    static void remove(String id) {
        DATA.remove(id); // reachable → eligible for GC
    }
}
