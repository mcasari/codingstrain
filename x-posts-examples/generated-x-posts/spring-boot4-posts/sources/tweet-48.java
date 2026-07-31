// ✅ Boot 4 default test stack
@SpringBootTest
class PricingServiceTest {

    @Autowired PricingService pricing;

    @Test
    void discounts() {
        assertThat(pricing.discount(100, "VIP"))
            .isEqualByComparingTo("90.00");
    }
}

// spring-boot-starter-webmvc-test (pulls JUnit 5, AssertJ, Mockito)
