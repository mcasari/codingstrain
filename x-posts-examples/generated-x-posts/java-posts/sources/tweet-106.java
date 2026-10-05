class Averages {
    static double of(List<Integer> values) {
        if (values == null || values.isEmpty())
            throw new IllegalArgumentException("no values");
        return values.stream()
            .mapToInt(i -> i)
            .average()
            .orElseThrow();
    }
}

// ❌ Happy path only — null / empty never run
@Test
void average_typicalList() {
    assertEquals(2.0, Averages.of(List.of(1, 2, 3)));
}

// ✅ Edges — null, empty, single element
@Test
void average_null_throws() {
    assertThrows(IllegalArgumentException.class,
        () -> Averages.of(null));
}

@Test
void average_empty_throws() {
    assertThrows(IllegalArgumentException.class,
        () -> Averages.of(List.of()));
}

@Test
void average_oneValue() {
    assertEquals(42.0, Averages.of(List.of(42)));
}
