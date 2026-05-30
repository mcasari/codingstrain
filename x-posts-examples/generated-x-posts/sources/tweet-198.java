@Cacheable("users")
public User findById(Long id) {
    return repo.findById(id).orElseThrow();
}
// Enable with @EnableCaching
