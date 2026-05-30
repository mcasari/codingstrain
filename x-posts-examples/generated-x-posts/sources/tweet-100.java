public class Worker {
    // Without volatile a thread may read a stale, cached value
    private volatile boolean running = true;

    public void stop() { running = false; }

    public void run() {
        while (running) {
            // sees stop() called from another thread
        }
    }
}
