// ❌ Loads every row — slow and memory-heavy
@GetMapping("/users")
List<User> all() {
    return users.findAll();
}

// ✅ Pageable in, Page out — one page at a time
@GetMapping("/users")
Page<User> page(Pageable pageable) {
    return users.findAll(pageable); // ?page=0&size=20&sort=name
}
