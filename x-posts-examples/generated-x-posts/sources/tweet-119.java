// Arrow form: no fall-through, returns a value
String label = switch (status) {
    case ACTIVE  -> "running";
    case PAUSED  -> "on hold";
    case STOPPED -> "done";
};
