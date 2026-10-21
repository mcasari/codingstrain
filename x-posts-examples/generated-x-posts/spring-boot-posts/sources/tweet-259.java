// ❌ Always load — blows up if Redis isn’t on the classpath
@Configuration
class RedisAutoConfig {
    @Bean
    RedisTemplate<String, String> redisTemplate() {
        return new RedisTemplate<>();
    }
}

// ✅ Only when Redis classes exist
@Configuration
@ConditionalOnClass(RedisTemplate.class)
class RedisAutoConfig {
    @Bean
    @ConditionalOnMissingBean
    RedisTemplate<String, String> redisTemplate() {
        return new RedisTemplate<>();
    }
}
