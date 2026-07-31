# application.yml
spring:
  data:
    redis:
      masterreplica:
        nodes:
          - redis://master:6379
          - redis://replica-1:6379
          - redis://replica-2:6379

// Use RedisTemplate / StringRedisTemplate as usual
@Bean
StringRedisTemplate redis(RedisConnectionFactory factory) {
    return new StringRedisTemplate(factory);
}
