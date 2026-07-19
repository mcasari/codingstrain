// ✅ Still first-class in Boot 4
// spring-boot-starter-session-data-redis
// spring-boot-starter-session-jdbc

# application.yml — Redis sessions
spring:
  session:
    store-type: redis
  data:
    redis:
      host: localhost

// For Hazelcast/MongoDB sessions: add the vendor-supported
// Spring Session module and follow their Boot 4 docs.
