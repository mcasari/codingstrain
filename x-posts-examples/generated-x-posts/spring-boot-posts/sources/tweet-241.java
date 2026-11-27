# ❌ Default — every @SpringBootTest reprints the startup summary
# spring.main.log-startup-info=true  (implicit)

# ✅ application-test.yml — quieter CI / test output
spring:
  main:
    log-startup-info: false
    banner-mode: off
