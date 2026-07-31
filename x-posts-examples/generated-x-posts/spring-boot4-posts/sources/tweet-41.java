@Bean
ApiVersionResolver headerVersion() {
    return request -> request.getHeader("X-API-Version");
}

@Bean
ApiVersionDeprecationHandler deprecations() {
    return (version, response) -> {
        if ("1.0".equals(version.toString())) {
            response.setHeader("Deprecation", "true");
            response.setHeader("Sunset", "Sat, 01 Aug 2026 00:00:00 GMT");
        }
    };
}
