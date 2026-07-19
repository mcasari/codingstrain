# ❌ Boot 3
spring:
  dao:
    exceptiontranslation:
      enabled: true

# ✅ Boot 4
spring:
  persistence:
    exceptiontranslation:
      enabled: true

// Still translates DataAccessException → @Repository proxies
