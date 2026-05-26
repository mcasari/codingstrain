// Always wrap JPA streams in try-with-resources:
//
// try (Stream<User> stream = repository.streamAllUsers()) { ... }
//
// Forgot it? Connection leak.
