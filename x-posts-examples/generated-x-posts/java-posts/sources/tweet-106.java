import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

static double average(List<Integer> values) {
    if (values == null || values.isEmpty())
        throw new IllegalArgumentException("no values");
    return values.stream().mapToInt(i -> i).average().orElseThrow();
}

// ❌ Happy path only — misses null and empty-list bugs
@Test
void average_typicalList() {
    assertEquals(2.0, average(List.of(1, 2, 3)));
}

// ✅ Edge cases — null, empty, and single element
@Test void average_null_throws() {
    assertThrows(IllegalArgumentException.class, () -> average(null));
}
@Test void average_empty_throws() {
    assertThrows(IllegalArgumentException.class, () -> average(List.of()));
}
@Test void average_oneValue() {
    assertEquals(42.0, average(List.of(42)));
}
