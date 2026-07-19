<!-- optional local dependency -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>

# application-local.yml
spring:
  devtools:
    livereload:
      enabled: true
    restart:
      enabled: true
