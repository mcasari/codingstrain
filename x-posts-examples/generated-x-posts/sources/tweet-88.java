// Immutable, null-hostile, concise
List<String> roles = List.of("ADMIN", "USER");
Map<String, Integer> limits = Map.of("free", 10, "pro", 100);

// roles.add("X") -> UnsupportedOperationException
