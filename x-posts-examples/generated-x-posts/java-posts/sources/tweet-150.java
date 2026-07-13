// Type is still static — just inferred by the compiler
var users = new ArrayList<User>();
var entry = Map.entry("k", 1);
for (var u : users) {
    System.out.println(u.getName());
}
