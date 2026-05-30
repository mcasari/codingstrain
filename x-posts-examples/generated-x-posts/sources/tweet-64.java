package com.codingstrain.junit5;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * JUnit 5 Extension API: one composable model that replaces JUnit 4's
 * {@code @RunWith} Runner (single, exclusive) and {@code @Rule} (inheritance-based).
 */
class TimingExtension implements BeforeEachCallback, ParameterResolver {

    private static final ExtensionContext.Namespace NS =
        ExtensionContext.Namespace.create(TimingExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        context.getStore(NS).put("start", System.nanoTime());
    }

    @Override
    public boolean supportsParameter(ParameterContext pc, ExtensionContext ec) {
        return pc.getParameter().getType() == Stopwatch.class;
    }

    @Override
    public Object resolveParameter(ParameterContext pc, ExtensionContext ec) {
        long start = ec.getStore(NS).get("start", long.class);
        return new Stopwatch(start);
    }
}

// No inheritance, no Runner: just declare the extensions you want.
@ExtendWith(TimingExtension.class)
class OrderServiceTest {

    @Test
    void placesOrder(Stopwatch stopwatch) {   // injected by ParameterResolver
        // ... exercise the system under test ...
        System.out.println("elapsed = " + stopwatch.elapsedMillis() + "ms");
    }
}
