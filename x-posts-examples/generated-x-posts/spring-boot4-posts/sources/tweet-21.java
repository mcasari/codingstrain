<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-restclient</artifactId>
</dependency>

@Bean
RestClient catalogClient(RestClient.Builder builder) {
    return builder
        .baseUrl("https://catalog.internal")
        .build();
}

# application.yml
spring:
  http:
    clients:
      connect-timeout: 2s
      read-timeout: 5s
