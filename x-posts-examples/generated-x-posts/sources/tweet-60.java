package com.codingstrain.varhandle;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * Demonstrates VarHandle fence operations that are weaker (and cheaper) than
 * {@code volatile}:
 *
 * <ul>
 *   <li>{@code setOpaque} — single-writer ordering, no full barrier</li>
 *   <li>{@code setRelease} — publish writes (release-store)</li>
 *   <li>{@code getAcquire} — subscribe (acquire-load) so prior writes are visible</li>
 * </ul>
 */
public final class VarHandleFencesDemo {

    /** Plain field accessed through a VarHandle (no {@code volatile} keyword). */
    static final class Holder {
        boolean flag;
        int payload;
    }

    private static final VarHandle FLAG;
    private static final VarHandle PAYLOAD;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            FLAG = lookup.findVarHandle(Holder.class, "flag", boolean.class);
            PAYLOAD = lookup.findVarHandle(Holder.class, "payload", int.class);
        } catch (ReflectiveOperationException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        opaqueExample();
        releaseAcquireExample();
    }

    /**
     * {@code setOpaque} / {@code getOpaque}: guarantees a single writer's updates
     * become visible in program order, without the stronger barriers of volatile.
     */
    static void opaqueExample() {
        Holder holder = new Holder();

        FLAG.setOpaque(holder, true);
        boolean current = (boolean) FLAG.getOpaque(holder);

        System.out.println("[opaque] single-writer ordering, flag = " + current);
    }

    /**
     * {@code setRelease} (publish) pairs with {@code getAcquire} (subscribe):
     * everything written before the release-store is visible to a thread that
     * observes the value via an acquire-load.
     */
    static void releaseAcquireExample() throws InterruptedException {
        Holder holder = new Holder();

        Thread publisher = new Thread(() -> {
            holder.payload = 42;                 // ordinary write
            FLAG.setRelease(holder, true);       // publish: release-store
        }, "publisher");

        Thread subscriber = new Thread(() -> {
            while (!(boolean) FLAG.getAcquire(holder)) {
                Thread.onSpinWait();             // subscribe: acquire-load
            }
            // Guaranteed to see payload == 42 because of release/acquire pairing.
            System.out.println("[release/acquire] observed payload = " + holder.payload);
        }, "subscriber");

        subscriber.start();
        publisher.start();
        publisher.join();
        subscriber.join();
    }
}
