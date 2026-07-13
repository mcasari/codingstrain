@MockBean
private MyService myService;

@Test @Order(1) void testA() {
    when(myService.getData()).thenReturn("A");
}
