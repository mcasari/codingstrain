@Test @Order(1) void testA() {
  when(myService.getData()).thenReturn("A");
}
@Test @Order(2) void testB() { /* still "A"? */ }
