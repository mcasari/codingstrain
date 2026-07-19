// ❌ Boot 3 habit — may not replace Boot 4 JSON mapper
@Bean
ObjectMapper objectMapper() {
    return new ObjectMapper().findAndRegisterModules();
}

// ✅ Boot 4
@Bean
JsonMapper jsonMapper(JsonMapper.Builder builder) {
    return builder
        .enable(SerializationFeature.INDENT_OUTPUT)
        .build();
}

@Bean
JsonMapperBuilderCustomizer dates() {
    return b -> b.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
}
