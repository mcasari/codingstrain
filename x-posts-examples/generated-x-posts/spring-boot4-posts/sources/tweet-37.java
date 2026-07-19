@SpringBootTest
@Testcontainers
class OrderRepositoryIT {

    @Container
    @ServiceConnection
    static MongoDBAtlasLocalContainer mongo =
        new MongoDBAtlasLocalContainer("mongodb/mongodb-atlas-local:8.0.4");

    @Autowired
    OrderRepository orders;

    @Test
    void saves() {
        orders.save(new Order("A-1"));
        assertThat(orders.findById("A-1")).isPresent();
    }
}
