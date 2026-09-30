// ❌ Verbose compare — easy to get the order wrong
users.sort((a, b) -> {
    int byLast = a.getLastName().compareTo(b.getLastName());
    if (byLast != 0) return byLast;
    return a.getFirstName().compareTo(b.getFirstName());
});

// ✅ comparing + thenComparing
users.sort(
    Comparator.comparing(User::getLastName)
              .thenComparing(User::getFirstName)
);
