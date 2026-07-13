// ❌ Blocking — the thread waits at every I/O call
User user = userClient.fetch(id);
List<Order> orders = orderClient.fetchForUser(user.getId());
System.out.println(user.getName() + ": " + orders.size());

// ✅ CompletableFuture — chain async steps, thread stays free
CompletableFuture
    .supplyAsync(() -> userClient.fetch(id))
    .thenApply(User::getId)
    .thenCompose(uid -> orderClient.fetchOrdersAsync(uid))
    .thenAccept(orders ->
        System.out.println("orders: " + orders.size()))
    .exceptionally(ex -> {
        log.error("lookup failed", ex);
        return null;
    });

// Run two calls in parallel, merge when both finish
CompletableFuture<User> userF =
    CompletableFuture.supplyAsync(() -> fetchUser(id));
CompletableFuture<Profile> profileF =
    CompletableFuture.supplyAsync(() -> fetchProfile(id));

userF.thenCombine(profileF,
        (u, p) -> u.getName() + " — " + p.getBio())
    .thenAccept(System.out::println);
