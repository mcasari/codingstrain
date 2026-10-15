package com.codingstrain.virtualthreads;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runnable sample for Tweet #113 — virtual threads for blocking I/O.
 *
 * <p>Simulates many concurrent "HTTP" calls with {@link Thread#sleep}. A small
 * platform pool finishes slowly; a virtual-thread executor keeps the same
 * blocking style but scales the waits.
 */
public final class VirtualThreadsDemo {

    private static final int TASKS = 50;
    private static final Duration BLOCK = Duration.ofMillis(100);
    private static final int PLATFORM_POOL = 8;

    public static void main(String[] args) throws Exception {
        List<String> urls = new ArrayList<>(TASKS);
        for (int i = 1; i <= TASKS; i++) {
            urls.add("https://api.example.com/items/" + i);
        }

        System.out.printf("Simulating %d blocking calls (%d ms each)%n%n", TASKS, BLOCK.toMillis());

        run("Platform fixed pool (" + PLATFORM_POOL + " threads)",
                Executors.newFixedThreadPool(PLATFORM_POOL), urls);

        run("Virtual thread per task",
                Executors.newVirtualThreadPerTaskExecutor(), urls);

        System.out.println();
        System.out.println("Tip: platform threads are limited OS threads; virtual threads");
        System.out.println("unmount while blocked, so many waits share few carriers.");
    }

    static void run(String label, ExecutorService exec, List<String> urls) throws Exception {
        AtomicInteger maxConcurrent = new AtomicInteger();
        AtomicInteger inFlight = new AtomicInteger();

        Instant start = Instant.now();
        try (exec) {
            List<Callable<String>> tasks = urls.stream()
                    .map(url -> (Callable<String>) () -> fetch(url, inFlight, maxConcurrent))
                    .toList();
            List<Future<String>> futures = exec.invokeAll(tasks);
            for (Future<String> f : futures) {
                f.get();
            }
        }
        Duration elapsed = Duration.between(start, Instant.now());

        System.out.printf("%-36s  elapsed=%4d ms  peak in-flight=%d%n",
                label, elapsed.toMillis(), maxConcurrent.get());
    }

    /** Fake blocking HTTP GET. */
    static String fetch(String url, AtomicInteger inFlight, AtomicInteger maxConcurrent)
            throws InterruptedException {
        int now = inFlight.incrementAndGet();
        maxConcurrent.accumulateAndGet(now, Math::max);
        try {
            Thread.sleep(BLOCK);
            return "ok:" + url;
        } finally {
            inFlight.decrementAndGet();
        }
    }

    private VirtualThreadsDemo() {
    }
}
