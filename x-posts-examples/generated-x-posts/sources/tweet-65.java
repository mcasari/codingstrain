// ❌ Type checks scattered everywhere
if (animal instanceof Dog d) { d.bark(); }
else if (animal instanceof Cat c) { c.meow(); }

// ✅ Let polymorphism decide
animal.makeSound();
