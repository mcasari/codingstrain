String a = null;
String b = "hi";

// ❌ NPE when the left side is null
boolean bad = a.equals(b);

// ✅ Null-safe — no crash
boolean good = Objects.equals(a, b); // false

Objects.equals(null, null);  // true
Objects.equals("hi", "hi"); // true
Objects.equals("hi", null);  // false

// Real-world use inside equals()
public class User {
    Long id;
    String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User u)) return false;
        return Objects.equals(id, u.id)
            && Objects.equals(email, u.email);
    }
}
