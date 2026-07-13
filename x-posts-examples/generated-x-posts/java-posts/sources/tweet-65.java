// ❌ BEFORE: instanceof chains to fork behavior
public String describe(Animal animal) {
    if (animal instanceof Dog) {
        return "Woof";
    } else if (animal instanceof Cat) {
        return "Meow";
    } else if (animal instanceof Cow) {
        return "Moo";
    }
    throw new IllegalArgumentException("Unknown animal");
}
// Add an animal => you must edit this method

// ✅ AFTER: let each type own its behavior
public interface Animal {
    String sound();
}

public final class Dog implements Animal {
    public String sound() { return "Woof"; }
}

public final class Cat implements Animal {
    public String sound() { return "Meow"; }
}

public final class Cow implements Animal {
    public String sound() { return "Moo"; }
}

// Call site: no type checks, no casts
public String describe(Animal animal) {
    return animal.sound();
}
// Add an animal => just add a class (open/closed)
