@SpringBootTest
@Sql("/test-data.sql")
class UserRepositoryTest {

    @Autowired UserRepository repo;

    @Test
    void testUserLoadedFromSql() {
        var user = repo.findById(1L).orElseThrow();
        assertEquals("Alice", user.getName());
    }
}
