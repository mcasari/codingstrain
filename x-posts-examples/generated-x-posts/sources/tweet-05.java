@GetMapping("/api/users/{id}")
public String getUser(@PathVariable Long id) {
  if (id == 1) return "User found";
  throw new RuntimeException("Database connection failed!");
}
