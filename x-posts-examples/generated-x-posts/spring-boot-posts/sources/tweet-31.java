registry.addMapping("/**")
    .allowedOrigins("http://localhost:3000")
    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    .allowCredentials(true);
