// ❌ isPresent() + get() — noisy branches
Optional<User> opt = repo.findById(id);
if (opt.isPresent()) {
    sendWelcome(opt.get().getEmail());
} else {
    log.warn("User {} not found", id);
}

// ✅ ifPresentOrElse — both branches in one call
repo.findById(id).ifPresentOrElse(
    u -> sendWelcome(u.getEmail()),
    () -> log.warn("User {} not found", id)
);
