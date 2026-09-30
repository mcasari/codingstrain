// ❌ Legacy Stack — extends Vector, synchronized every call
Stack<String> undo = new Stack<>();
undo.push("typed hello");
undo.push("added space");
String last = undo.pop(); // "added space" (LIFO)

// ✅ Deque as a stack — same API, modern and faster
Deque<String> undo2 = new ArrayDeque<>();
undo2.push("typed hello");
undo2.push("added space");
String last2 = undo2.pop(); // "added space" — same LIFO
