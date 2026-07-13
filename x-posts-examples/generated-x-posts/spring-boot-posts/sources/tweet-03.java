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
