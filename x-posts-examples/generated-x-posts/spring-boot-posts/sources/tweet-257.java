// ✅ Config Server — Git is the source of truth
@SpringBootApplication
@EnableConfigServer
class ConfigServerApp { }
# application.yml (server)
spring:
  cloud:
    config:
      server:
        git:
          uri: https://github.com/org/config-repo

# ✅ Client — fetch shared YAML at startup
spring:
  application:
    name: order-service
  config:
    import: "optional:configserver:http://localhost:8888"
