@Bean
CommandLineRunner init(UserRepository repo) {
    return args -> repo.save(new User("Mario"));
}
// Runs once, right after the context starts
