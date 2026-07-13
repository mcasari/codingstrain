# Java 14+ Switch Expressions — Exhaustiveness Demo

Runnable sample for the tweet:

> Java 14+ switch expressions return a value and the compiler checks you handled every enum constant — forget a case and the build fails before production.

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

- `x-posts-examples/generated-x-posts/java-posts/sources/tweet-55.java`
