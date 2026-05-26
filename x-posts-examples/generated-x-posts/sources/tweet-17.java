try (Stream<User> stream = repository.streamAllUsers()) {
    stream.forEach(this::process);
}
