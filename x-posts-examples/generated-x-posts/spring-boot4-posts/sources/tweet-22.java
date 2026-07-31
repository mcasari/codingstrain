<!-- ❌ Incomplete in Boot 4 -->
<dependency>
  <groupId>org.springframework.security</groupId>
  <artifactId>spring-security-test</artifactId>
  <scope>test</scope>
</dependency>

<!-- ✅ Boot 4 -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security-test</artifactId>
  <scope>test</scope>
</dependency>

@SpringBootTest
@WithMockUser(roles = "ADMIN")
class AdminApiTest { }
