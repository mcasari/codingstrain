# Java Virtual Threads Demo

Runnable sample for Tweet #113 (virtual threads for blocking I/O).

Requires **Java 21+**.

## Run

```bash
cd x-posts-examples/java-virtual-threads-demo
mvn -q compile exec:java
```

## What it does

- Simulates **50** concurrent “HTTP” calls with `Thread.sleep(100)` (no network)
- Runs the same work on:
  - a **fixed platform pool** (8 threads) — waits queue for a free OS thread
  - **`Executors.newVirtualThreadPerTaskExecutor()`** — one virtual thread per call
- Prints elapsed time and peak in-flight tasks so you can see the difference

## Related tweet asset

- `x-posts-examples/generated-x-posts/java-posts/sources/tweet-113.java`
