// ❌ equals without hashCode
class BadUser {
    private final String email;
    BadUser(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        return o instanceof BadUser u && email.equals(u.email);
    }
}

new HashSet<>(List.of(new BadUser("a@x.com")))
    .contains(new BadUser("a@x.com")); // false

// ✅ hashCode uses the same fields as equals
class GoodUser {
    private final String email;
    GoodUser(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        return o instanceof GoodUser u && email.equals(u.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}

new HashSet<>(List.of(new GoodUser("a@x.com")))
    .contains(new GoodUser("a@x.com")); // true
