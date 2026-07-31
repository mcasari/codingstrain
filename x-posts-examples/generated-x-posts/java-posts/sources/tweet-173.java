import java.time.Instant;
import java.time.temporal.ChronoUnit;

Instant a = Instant.parse("2026-07-27T10:15:30.123Z");
Instant b = Instant.parse("2026-07-27T18:45:00.999Z");

// ❌ Full precision — different times, equals is false
a.equals(b);  // false (expected)

// Same calendar day in UTC? nanos/hours still spoil a naive check
Instant morning = Instant.parse("2026-07-27T08:00:00.001Z");
Instant evening = Instant.parse("2026-07-27T20:00:00.999Z");
morning.equals(evening);  // false

// ✅ Truncate to the unit you care about, then compare
morning.truncatedTo(ChronoUnit.DAYS)
    .equals(evening.truncatedTo(ChronoUnit.DAYS));  // true

// Same hour?
a.truncatedTo(ChronoUnit.HOURS)
    .equals(Instant.parse("2026-07-27T10:59:59Z")
        .truncatedTo(ChronoUnit.HOURS));  // true

// Also: MINUTES, SECONDS, …
