# JUnit 5 Extension API — BeforeEachCallback + ParameterResolver

Runnable sample for the tweet:

> JUnit 5's Extension API replaces JUnit 4's Runner and `@Rule`; `BeforeEachCallback`
> and `ParameterResolver` compose cleanly without inheritance.

## Run

```bash
cd x-posts-examples/junit5-extension-api-demo
mvn -q test
```

## What this demonstrates

| JUnit 4 | JUnit 5 |
|---------|---------|
| `@RunWith(Runner.class)` — one Runner per class, mutually exclusive | `@ExtendWith({...})` — stack as many extensions as you want |
| `@Rule` / `TestRule` — often needs a base class or field plumbing | Extension points implemented on a plain class, no inheritance |
| Inject behavior via inheritance or boilerplate | `ParameterResolver` injects dependencies straight into test methods |

`TimingExtension` implements two independent extension points:

- `BeforeEachCallback` — records a start time before each test
- `ParameterResolver` — injects a `Stopwatch` into any test method that declares one

`LoggingExtension` is a separate extension stacked alongside it via `@ExtendWith`,
showing that extensions **compose** — no Runner conflict, no `@Rule` chain, no shared
base class. The test class (`OrderServiceTest`) extends nothing.

## Files

| File | Role |
|------|------|
| `TimingExtension` | `BeforeEachCallback` + `ParameterResolver` |
| `LoggingExtension` | `BeforeEachCallback` + `AfterEachCallback` (composition) |
| `Stopwatch` | Injected per-test dependency |
| `OrderService` | Trivial system under test |
| `OrderServiceTest` | `@ExtendWith({...})`, no inheritance |

## Related tweet asset

- `x-posts-examples/generated-x-posts/java-posts/sources/tweet-57.java`
