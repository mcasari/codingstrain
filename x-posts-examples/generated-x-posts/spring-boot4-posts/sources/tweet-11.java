// STEP 1 — add migrator (temporary)
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-properties-migrator</artifactId>
  <scope>runtime</scope>
</dependency>

// It warns: old key → new key
// spring.dao.exceptiontranslation.enabled
//   → spring.persistence.exceptiontranslation.enabled

// STEP 2 — fix application.yml, then remove migrator
# ❌ old
spring.dao.exceptiontranslation.enabled: true

# ✅ new
spring.persistence.exceptiontranslation.enabled: true
