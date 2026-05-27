# Tweet — Java 17 sealed classes

## Primary (technical, ~280 chars)

```
💡 Java 17 — sealed types close the inheritance graph with `permits`.

Only listed subtypes may extend the sealed class/interface.
Each permitted subtype must be `final`, `sealed`, or `non-sealed`.

`non-sealed` is the escape hatch: inheritance opens again below that branch.

Diagram + runnable sample in repo 👇
#Java #Java17
```

## Alternate (shorter hook)

```
Sealed classes (Java 17) = closed hierarchies via `permits`.

Compiler checks:
✓ only permitted types can extend
✓ each must be final | sealed | non-sealed

`non-sealed` re-opens the tree — see FancyRectangle → RoundedRectangle.

#Java17
```

## Thread reply (optional elaboration)

```
1/ `abstract sealed class Shape permits Circle, Rectangle, FancyRectangle`
   → Shape’s direct subclasses are fixed at compile time.

2/ `final` (Circle, Square, Oblong) = leaf nodes.

3/ `sealed class Rectangle permits Square, Oblong`
   → nested closed set under Shape.

4/ `non-sealed class FancyRectangle`
   → not a leaf in the *global* model; any class may extend it
   (e.g. RoundedRectangle) without being in Shape’s permits clause.

5/ Pair with exhaustive pattern matching (Java 21+ switch) or
   sealed interfaces for domain ADTs — fewer “surprise” subtypes in prod.
```

## Media

Attach: `diagrams/java-17-sealed-classes.png` (export from `.drawio` if needed).

Link in bio / reply: `x-posts-examples/java-17-sealed-classes`
