// ❌ Manual inserts clutter the test
@Test
void findsAlice() {
    jdbc.update("INSERT INTO users(id,name) VALUES (1,'Alice')");
    assertEquals("Alice", repo.findById(1L).orElseThrow().getName());
}

// ✅ @Sql — script prepares the DB
@SpringBootTest
@Sql("/test-data.sql")  // INSERT INTO users … Alice
class UserRepositoryTest {
    @Autowired UserRepository repo;

    @Test
    void findsAlice() {
        assertEquals("Alice",
            repo.findById(1L).orElseThrow().getName());
    }
}
