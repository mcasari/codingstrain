// ❌ try-catch for expected paths — slow and hides real errors
int parseAge(String input) {
    try {
        return Integer.parseInt(input);
    } catch (NumberFormatException e) {
        return 0;   // "invalid" is normal input, not an exception
    }
}

Optional<User> findByIndex(List<User> users, int index) {
    try {
        return Optional.of(users.get(index));
    } catch (IndexOutOfBoundsException e) {
        return Optional.empty();
    }
}

// ✅ Use normal control flow for expected cases — reserve catch for failures
int parseAge(String input) {
    if (input == null || !input.matches("-?\d+")) {
        return 0;
    }
    return Integer.parseInt(input);
}

Optional<User> findByIndex(List<User> users, int index) {
    if (index < 0 || index >= users.size()) {
        return Optional.empty();
    }
    return Optional.of(users.get(index));
}
