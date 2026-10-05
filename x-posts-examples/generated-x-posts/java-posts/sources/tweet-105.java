class PriceService {
    double tax(double net) {
        if (net < 0)
            throw new IllegalArgumentException("net");
        return net * 0.19;
    }
}

// ❌ Covered — but proves almost nothing
@Test
void tax_runs() {
    assertNotNull(new PriceService().tax(100));
}

// ✅ Assert the outcome and the failure path
@Test
void tax_appliesRate() {
    assertEquals(19.0, new PriceService().tax(100), 0.001);
}

@Test
void tax_rejectsNegative() {
    assertThrows(IllegalArgumentException.class,
        () -> new PriceService().tax(-1));
}
