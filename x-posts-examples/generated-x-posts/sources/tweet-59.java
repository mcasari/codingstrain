enum Status {
    NEW,
    RUNNING,
    DONE
}

public static void main(String[] args) {
    List<Status> statuses = List.of(Status.NEW, Status.RUNNING, Status.DONE);

    for (Status status : statuses) {
        String message = toLabel(status);
        System.out.printf("%s -> %s%n", status, message);
    }

    System.out.println();
    System.out.println("Tip: add FAILED to Status and this switch expression");
    System.out.println("will not compile until you handle FAILED or add default.");
}

static String toLabel(Status status) {
    return switch (status) {
        case NEW -> "queued";
        case RUNNING -> "in progress";
        case DONE -> "finished";
    };
}
