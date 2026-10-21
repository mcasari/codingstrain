# ❌ Lazy=true — @PostConstruct waits until first use
spring:
  main:
    lazy-initialization: true

@Service
class CacheWarmer {
    @PostConstruct
    void warm() {
        cache.loadAll(); // NOT at startup when lazy
    }
}

# ✅ Need init at boot? Stay eager (default) or force the bean
spring:
  main:
    lazy-initialization: false   # default — PostConstruct at startup

# Or keep lazy, but touch critical beans early:
@Bean
ApplicationRunner touch(CacheWarmer warmer) {
    return args -> { /* injecting warmer creates it now */ };
}
