enum Status { NEW, RUNNING, DONE }

Status status = Status.RUNNING;

// ❌ Old switch — easy to forget break
String label;
switch (status) {
    case NEW:     label = "queued";       break;
    case RUNNING: label = "in progress"; break;
    case DONE:    label = "finished";     break;
}

// ✅ Switch expression — returns a value, no break needed
String label = switch (status) {
    case NEW     -> "queued";
    case RUNNING -> "in progress";
    case DONE    -> "finished";
};

// What happens if you add a FAILED status and skip it in the example above?
// Won't compile until you handle FAILED (or add default)
