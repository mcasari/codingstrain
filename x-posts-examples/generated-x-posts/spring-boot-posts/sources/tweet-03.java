// ❌ Manual paging — easy to get offset wrong
@GetMapping
public List<User> getUsers(
        @RequestParam int page,
        @RequestParam int size) {
    int offset = page * size;
    return repository.findAll()
        .stream()
        .skip(offset)
        .limit(size)
        .toList();  // no total count either
}

// ✅ Spring Data binds ?page=0&size=20 for you
@GetMapping
public PagedResponse<User> getUsers(Pageable pageable) {
    Page<User> page = repository.findAll(pageable);
    return new PagedResponse<>(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getTotalPages()
    );
}

// GET /users?page=0&size=20&sort=lastName,asc
