package com.codingstrain.junit5;

/**
 * Per-test stopwatch. Instances are created by {@link TimingExtension} and
 * injected into test methods via the {@code ParameterResolver} contract — the
 * test class never has to extend a base class to obtain one.
 */
public final class Stopwatch {

    private final long startNanos;

    public Stopwatch(long startNanos) {
        this.startNanos = startNanos;
    }

    public long elapsedMillis() {
        return (System.nanoTime() - startNanos) / 1_000_000;
    }
}
