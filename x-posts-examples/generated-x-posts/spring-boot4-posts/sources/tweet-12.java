<!-- temporary — runtime scope -->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-properties-migrator</artifactId>
  <scope>runtime</scope>
</dependency>

// Startup log will flag things like:
// management.tracing.enabled → management.tracing.export.enabled
// spring.dao.exceptiontranslation.enabled
//   → spring.persistence.exceptiontranslation.enabled

// When quiet: delete the dependency.
