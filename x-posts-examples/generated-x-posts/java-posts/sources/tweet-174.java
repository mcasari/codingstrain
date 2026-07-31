List<String> names = List.of("ada", "grace", "linus");

// ❌ Business logic in peek — fragile and surprising
List<String> bad = names.stream()
    .peek(n -> audit.log("seen " + n))   // side effect
    .peek(n -> n = n.toUpperCase())      // does nothing useful (n is local)
    .map(String::toUpperCase)
    .toList();

// findAny() may process only one element — later peeks never run
names.stream()
    .peek(n -> counter.increment())
    .findAny();

// ✅ peek for debug only; real work in map / forEach
List<String> good = names.stream()
    .peek(n -> System.out.println("debug: " + n))  // temporary
    .map(String::toUpperCase)
    .toList();

names.stream()
    .map(String::toUpperCase)
    .forEach(audit::log);  // intentional side effect at the end
