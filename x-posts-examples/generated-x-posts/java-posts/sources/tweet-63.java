// ❌ Too many parameters — easy to pass them in the wrong order
public void createUser(String firstName, String lastName,
                       String street, String city,
                       String zip, String country) {
    // ...
}

createUser("Mario", "Rossi", "Via Roma", "Rome", "00100", "IT");


// ✅ Group related values into a cohesive object
public record Address(String street, String city,
                      String zip, String country) {}

public void createUser(String firstName, String lastName,
                       Address address) {
    // ...
}

createUser("Mario", "Rossi",
    new Address("Via Roma", "Rome", "00100", "IT"));
