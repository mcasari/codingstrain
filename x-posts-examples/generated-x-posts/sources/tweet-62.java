enum Status { NEW, RUNNING, DONE }

String label = switch (status) {
    case NEW -> "queued";
    case RUNNING -> "in progress";
    case DONE -> "finished";
};

// Add FAILED to Status and the switch must be updated
// (or provide a default branch).
