<!-- ❌ Boot 3 habit: jar on classpath, hope auto-config appears -->
<dependency>
  <groupId>org.flywaydb</groupId>
  <artifactId>flyway-core</artifactId>
</dependency>

<!-- ✅ Boot 4: dedicated starter brings auto-config -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-flyway</artifactId>
</dependency>

<!-- Same rename pattern elsewhere -->
<!-- spring-boot-starter-liquibase -->
<!-- spring-boot-starter-mongodb -->
<!-- spring-boot-starter-restclient -->
<!-- spring-boot-starter-webmvc  (was starter-web) -->
