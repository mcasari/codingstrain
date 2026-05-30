package com.codingstrain.junit5;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

/**
 * JUnit 5 Extension API in action: one composable type that replaces JUnit 4's
 * {@code @RunWith} Runner (single, exclusive) and {@code @Rule} (inheritance-based).
 *
 * <p>It implements two independent extension points:
 * <ul>
 *   <li>{@link BeforeEachCallback} — a lifecycle hook that records a start time</li>
 *   <li>{@link ParameterResolver} — injects a {@link Stopwatch} into test methods</li>
 * </ul>
 * Both compose with other extensions simply by being listed in {@code @ExtendWith}.
 */
public class TimingExtension implements BeforeEachCallback, ParameterResolver {

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
