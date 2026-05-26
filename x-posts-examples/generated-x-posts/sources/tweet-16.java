try (Stream<User> stream = repository.streamAllUsers()) {
    stream.forEach(user -> {
        process(user);
        if (counter.incrementAndGet() % 100 == 0) {
            em.clear();
        }
    });
}
