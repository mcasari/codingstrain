// ❌ Magic numbers — what do these values mean?
if (sessionAgeSeconds > 86400 * 7) {
    logout(user);
}
double total = price * 1.21;
Thread.sleep(5000);

// ✅ Named constants — intent is obvious at the call site
private static final int SECONDS_PER_DAY = 86_400;
private static final int SESSION_DAYS = 7;
private static final double VAT_RATE = 0.21;
private static final long RETRY_DELAY_MS = 5_000;

if (sessionAgeSeconds > SECONDS_PER_DAY * SESSION_DAYS) {
    logout(user);
}
double total = price * (1 + VAT_RATE);
Thread.sleep(RETRY_DELAY_MS);
