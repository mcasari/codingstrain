// ❌ Big-bang rewrite — no safety net
// delete LegacyPricing, drop in NewPricing, deploy and hope

// ✅ Step 1 — lock existing behavior
@Test
void vipDiscount_staysTheSame() {
    assertEquals(90.0, pricing.total(100.0, "VIP"));
}

// ✅ Step 2 — tiny extract; tests still green
class LegacyPricing {
    double total(double amount, String tier) {
        return amount * rateFor(tier);
    }

    private double rateFor(String tier) {
        return "VIP".equals(tier) ? 0.9 : 1.0;
    }
}
