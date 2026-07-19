<!-- Driver-only service still gets /actuator/health -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-mongodb</artifactId>
</dependency>
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

# application.yml
spring:
  mongodb:
    uri: mongodb://localhost:27017/orders

management:
  endpoint:
    health:
      show-details: always
