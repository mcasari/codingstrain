import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

// ❌ Legacy Stack — extends Vector, synchronized on every call
Stack<String> undo = new Stack<>();
undo.push("typed 'hello'");
undo.push("added space");
undo.pop();   // removes "added space"

// ✅ Deque as a stack — same API, modern and faster
Deque<String> undo = new ArrayDeque<>();
undo.push("typed 'hello'");
undo.push("added space");
undo.pop();   // removes "added space" — same LIFO behavior
