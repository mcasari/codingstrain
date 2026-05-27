# Java 17 — Sealed classes (`permits`)

Sample project for the X post on **closed type hierarchies** with `sealed`, `permits`, and the three permitted-subclass modifiers.

## Run

```bash
cd x-posts-examples/java-17-sealed-classes
mvn -q compile exec:java
```

Or without Maven:

```bash
mkdir -p target/classes
find src -name '*.java' | xargs javac --release 17 -d target/classes
java -cp target/classes com.codingstrain.sealed.SealedClassesDemo
```


## Hierarchy (matches diagram)

```
Shape (abstract sealed, permits Circle, Rectangle, FancyRectangle)
├── Circle (final)
├── Rectangle (sealed, permits Square, Oblong)
│   ├── Square (final)
│   └── Oblong (final)
└── FancyRectangle (non-sealed)
    └── RoundedRectangle (ordinary class — open extension)
```
