// Compile once and reuse — not on every call
private static final Pattern EMAIL =
    Pattern.compile("^[^@\\s]+@[^@\\s]+$");

boolean ok = EMAIL.matcher(input).matches();
