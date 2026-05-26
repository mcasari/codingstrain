@SpringBootTest
@Sql("/test-data.sql")
class UserRepositoryTest {
  @Test void testUserLoadedFromSql() {
    assertEquals("Alice", repo.findById(1L).orElseThrow().getName());
  }
}
