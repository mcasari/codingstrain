@SpringBootTest
class OrderServiceTest {

    // ❌ Removed in Boot 4
    // @MockBean OrderRepository repo;

    // ✅ Spring Framework 7 / Boot 4
    @MockitoBean
    OrderRepository repo;

    @Autowired
    OrderService service;

    @Test
    void findsOrder() {
        when(repo.findById(1L)).thenReturn(Optional.of(new Order(1L)));
        assertThat(service.get(1L).id()).isEqualTo(1L);
    }
}
