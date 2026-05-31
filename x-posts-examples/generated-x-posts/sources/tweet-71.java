// ❌ Typo in the method name — compiles, but you did NOT override toString()
public class User {
    private final String name;

    public User(String name) { this.name = name; }

    public String toStrng() {   // meant toString() — new method, not an override
        return name;
    }
}
// Bug ships silently — println(user) won't use your method


// ✅ @Override turns a typo into a compile error
public class User {
    private final String name;

    public User(String name) { this.name = name; }

    @Override
    public String toString() {
        return "User(" + name + ")";
    }
}

// @Override public String toStrng() { ... }
// -> method does not override or implement a method from a supertype
