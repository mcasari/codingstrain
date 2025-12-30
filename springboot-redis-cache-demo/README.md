# Spring Boot Redis Cache Demo

## What this shows
- @Cacheable with Redis
- TTL-based cache expiration
- Preventing expensive API recomputation

## How to run

1. Start Redis:
   docker run -p 6379:6379 redis

2. Run the app:
   mvn spring-boot:run

3. Call endpoint:
   http://localhost:8080/weather?city=London

First call ~3s, next calls instant (cached).
Cache expires after 5 minutes.
