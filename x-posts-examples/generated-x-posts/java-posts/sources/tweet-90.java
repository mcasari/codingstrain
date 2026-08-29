// ❌ Sleep to wait — poll too often or too rarely
while (!ready) {
    Thread.sleep(100);
}

// ✅ Block until another thread signals
CountDownLatch done = new CountDownLatch(1);
done.await();        // waiting thread
done.countDown();    // worker when finished

// ❌ Sleep loop for periodic work
while (true) {
    doWork();
    Thread.sleep(1_000);
}

// ✅ Scheduler
ScheduledExecutorService exec =
    Executors.newSingleThreadScheduledExecutor();
exec.scheduleAtFixedRate(this::doWork, 0, 1, TimeUnit.SECONDS);
