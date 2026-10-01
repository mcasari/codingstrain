# ❌ Boot 3 — LiveReload on by default (port 35729)
# spring.devtools.livereload.enabled=true  (implicit)
# First IDE app grabbed the port; others silently skipped it

# ✅ Boot 4 — off unless you ask
# application-local.yml
spring:
  devtools:
    livereload:
      enabled: true   # only if you use a LiveReload browser extension
    restart:
      enabled: true   # automatic restart still defaults on

<!-- pom.xml — keep optional so it never ships to prod -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-devtools</artifactId>
  <optional>true</optional>
</dependency>
