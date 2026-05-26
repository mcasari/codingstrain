return RedisCacheConfiguration.defaultCacheConfig()
  .entryTtl(Duration.ofMinutes(5))
  .disableCachingNullValues();
