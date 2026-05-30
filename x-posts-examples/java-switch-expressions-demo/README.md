# Java 14+ Switch Expressions — Exhaustiveness Demo

Runnable sample for the tweet:

> switch expressions (Java 14+) are exhaustive: if you omit a case, the compiler errors unless you add a `default` or cover all enum constants.

## Run

```bash
cd x-posts-examples/java-switch-expressions-demo
mvn -q compile exec:java
```

Or with `javac`:

```bash
mkdir -p target/classes
find src -name '*.java' | xargs javac --release 17 -d target/classes
java -cp target/classes com.codingstrain.switchexpr.SwitchExpressionsDemo
```

## What this demonstrates

- `toLabel(Status)` uses a **switch expression** with no `default`.
- Because `Status` is an enum, the compiler enforces that all constants are handled.
- If you add a new constant (for example `FAILED`) and forget to update the switch, compilation fails.

## Related tweet asset

- `x-posts-examples/generated-x-posts/sources/tweet-59.java`
