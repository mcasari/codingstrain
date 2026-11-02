// ❌ Setters only — mutable, easy to miss required fields
class User {
    private String name;
    private int age;
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
}

// ✅ @JsonCreator — one explicit constructor, immutable fields
class User {
    private final String name;
    private final int age;

    @JsonCreator
    User(@JsonProperty("name") String name,
         @JsonProperty("age") int age) {
        this.name = name;
        this.age = age;
    }
}
