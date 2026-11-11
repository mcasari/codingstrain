// ❌ Keep stubs across tests — easy to get order-dependent failures
@SpringBootTest
class OrderServiceTest {
    @MockBean(reset = MockReset.NONE)
    PaymentClient payments; // stubbing survives into the next test
}

// ✅ Default — reset after each test (MockReset.AFTER)
@SpringBootTest
class OrderServiceTest {
    @MockBean  // same as reset = MockReset.AFTER
    PaymentClient payments;

    @Test
    void chargesCard() {
        when(payments.charge(any())).thenReturn(ok());
        // … assert …
    } // stub cleared before the next @Test
}
