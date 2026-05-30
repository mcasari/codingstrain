package com.codingstrain.junit5;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * A second, independent extension. Stacking it alongside {@link TimingExtension}
 * via {@code @ExtendWith} shows that extensions compose — no Runner conflict, no
 * deep {@code @Rule} chain, no shared base class.
 */
public class LoggingExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        System.out.println("[log] starting: " + context.getDisplayName());
    }

    @Override
    public void afterEach(ExtensionContext context) {
        System.out.println("[log] finished: " + context.getDisplayName());
    }
}
