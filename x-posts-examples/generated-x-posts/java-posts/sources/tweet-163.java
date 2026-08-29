List<String> names = users.stream()
    .filter(User::isActive)
    .map(User::getName)
    .sorted()
    .toList();
// Declarative: say WHAT you want, not HOW to loop
