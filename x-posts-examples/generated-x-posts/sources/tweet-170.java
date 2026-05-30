CompletableFuture
    .supplyAsync(() -> fetchUser(id))
    .thenApply(User::getName)
    .thenAccept(System.out::println);
