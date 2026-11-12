// ❌ Shared mutable list — races and lost updates
List<String> events = new ArrayList<>();
exec.submit(() -> events.add("A"));
exec.submit(() -> events.add("B")); // not thread-safe

// ✅ Pass messages — no shared mutable state
BlockingQueue<String> queue = new LinkedBlockingQueue<>();
exec.submit(() -> queue.offer("A"));
exec.submit(() -> queue.offer("B"));
String next = queue.take(); // consumer owns the hand-off
