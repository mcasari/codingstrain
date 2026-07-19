// ✅ Boot 4 auto-configures JsonMapper (Jackson 3)
@Bean
JsonMapperBuilderCustomizer pretty() {
    return builder -> builder.enable(SerializationFeature.INDENT_OUTPUT);
}

// Need Jackson 2 defaults while migrating?
# application.yml
spring:
  jackson:
    use-jackson2-defaults: true

// Last resort (deprecated): add spring-boot-jackson2
// Prefer migrating annotations/imports to tools.jackson.*
