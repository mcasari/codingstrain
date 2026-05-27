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

## Diagram ([diagrams.net](https://app.diagrams.net/))

Open in the browser or desktop app:

- Source: [`../diagrams/java-17-sealed-classes.drawio`](../diagrams/java-17-sealed-classes.drawio)
- Export: [`../diagrams/java-17-sealed-classes.png`](../diagrams/java-17-sealed-classes.png)

**Import:** [app.diagrams.net](https://app.diagrams.net/) → *Open* → choose the `.drawio` file.

## What the compiler enforces

| Rule | Meaning |
|------|---------|
| `sealed` + `permits` | Only listed types may extend the sealed class/interface |
| Permitted subtype modifier | Each listed subtype must be `final`, `sealed`, or `non-sealed` |
| `final` | No further subclasses (leaf) |
| `sealed` | Another closed level (`permits` required again) |
| `non-sealed` | Stops sealing — ordinary open inheritance below that type |

**Compile errors (try uncommenting in a scratch file):**

- `class Bad extends Shape` — not in `permits`
- `class Bad extends Shape` with `permits Bad` but no `final` / `sealed` / `non-sealed`
- `sealed class Rectangle` without `permits` when it has direct subclasses

## Tweet (ready to post)

See [`TWEET.md`](TWEET.md).

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
