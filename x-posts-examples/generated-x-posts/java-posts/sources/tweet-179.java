Optional<User> found = repo.findById(id);

// ❌ Null or empty Optional handled too late
User user = found.orElse(null);
return user.getEmail();   // NPE here, far from the lookup

// ✅ Fail fast — exception where the value was expected
User user = found.orElseThrow(
    () -> new UserNotFoundException(id));
return user.getEmail();

// Same idea in one chain
return repo.findById(id)
    .map(User::getEmail)
    .orElseThrow(() -> new UserNotFoundException(id));
