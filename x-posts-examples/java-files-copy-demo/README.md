# Java Files.copy demo

Runnable sample for Tweet #181 (`Files.copy`).

## Run

From the **java-files-copy-demo** folder (so `src/main/resources/data.csv` is found):

```bash
cd x-posts-examples/java-files-copy-demo
mvn -q compile exec:java
```

**IDE:** Open this folder as the project root (not the whole monorepo), or run after `mvn compile` so `data.csv` is on the classpath.

## What it does

- Loads `src/main/resources/data.csv` (classpath after compile, or directly from disk in the IDE)
- Copies it into a temp folder, then to `data.csv.bak` using `Files.copy(..., REPLACE_EXISTING)`
- Prints the backup contents

## Sample data

Edit `src/main/resources/data.csv` to try your own rows.

