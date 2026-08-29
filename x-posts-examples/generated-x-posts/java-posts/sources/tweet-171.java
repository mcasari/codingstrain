Object animal = new Dog("Rex");

// ❌ Classic instanceof + cast
if (animal instanceof Dog) {
    Dog d = (Dog) animal;  // redundant cast
    d.bark();
}

// ✅ Pattern matching — bind in the check (Java 16+)
if (animal instanceof Dog d) {
    d.bark();  // d is a Dog here
} else if (animal instanceof Cat c) {
    c.meow();
}
// d / c are NOT in scope outside their true branch
