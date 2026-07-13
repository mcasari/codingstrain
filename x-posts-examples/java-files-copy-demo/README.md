# Java Files.copy demo

Runnable sample for Tweet #181 (`Files.copy`).

## Run

```bash
cd x-posts-examples/java-files-copy-demo
mvn -q compile exec:java
```

## What it does

- Creates a temp folder
- Writes a small `data.csv`
- Copies it to `data.csv.bak` using `Files.copy(..., REPLACE_EXISTING)`
- Prints the backup contents

