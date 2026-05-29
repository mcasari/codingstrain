# Java VarHandle Fences — setOpaque / setRelease / getAcquire

Runnable sample for the tweet:

> VarHandle provides fence operations weaker than `volatile`: `setOpaque` for single-writer ordering, `setRelease` for publish, `getAcquire` for subscribe.

## Run

```bash
cd x-posts-examples/java-varhandle-fences-demo
mvn -q compile exec:java
```

Or with `javac`:

```bash
mkdir -p target/classes
find src -name '*.java' | xargs javac --release 17 -d target/classes
java -cp target/classes com.codingstrain.varhandle.VarHandleFencesDemo
```

## What this demonstrates

| Access mode | Role | Cost vs `volatile` |
|-------------|------|--------------------|
| `setOpaque` / `getOpaque` | Single-writer ordering, no full barrier | Cheapest |
| `setRelease` | Publish: release-store | Cheaper than volatile store |
| `getAcquire` | Subscribe: acquire-load | Cheaper than volatile load |

`setRelease` pairs with `getAcquire`: everything written before the release-store
is visible to a thread that reads the flag via an acquire-load. The fields are
plain (no `volatile` keyword) — ordering comes entirely from the VarHandle access modes.

## Related tweet asset

- `x-posts-examples/generated-x-posts/sources/tweet-63.java`
