List<User> users = List.of(
    new User(null, "Ada"),
    new User("Lovelace", "Ada"),
    new User(null, "Grace")
);

// ❌ NPE — comparing() calls keyExtractor then key.compareTo
users.sort(Comparator.comparing(User::getLastName));

// ✅ Nulls first, then by last name, then first name
users.sort(
    Comparator.comparing(
        User::getLastName,
        Comparator.nullsFirst(String::compareTo))
    .thenComparing(
        User::getFirstName,
        Comparator.nullsLast(String::compareTo))
);

// nullsLast(...) — put missing values at the end instead
