// 💡SOLID principles: The Liskov Substitution Principle (LSP) states that objects of a superclass should be replaceable with objects of a subclass.
//
// ✅ Subclasses should not behave differently than the base class.
//
// ❌ Example that breaks LSP:
//
// class Vehicle {
//     public void startEngine() {
//         ...
//     }
// }
//
// class Bicycle extends Vehicle {
//
// @Override
//
//     public void startEngine() {
//         throw new UnsupportedOperationException("Bicycles don't have engines!");
//     }
// }
//
// If you write a test for the Vehicle class, you would expect every subclass could start the engine. But as we've seen, Bicycle can't, it has no engine and throws an Exception.
//
// This violates LSP. To fix it, we need to change the design so that only appropriate classes have the startEngine() behavior.
//
// ✅ We can for example define two separate interfaces:
//
// interface Vehicle {...}
//
// interface Motorized {
//     void startEngine();
// }
//
// class Car implements Vehicle, Motorized {
//     //Vehicle methods implementations
//
//      //Motorized implementation
//     public void startEngine() {
//          ...
//     }
// }
//
// class Bicycle implements Vehicle {
//     // No engine, it implements only Vehicle
// }
//
// ✅ An alternative could be creating a default Vehicle implementation, and extending it while implementing the Motorized interface only for vehicles that have engines.
//
// #SOLID #SoftwareDesign
