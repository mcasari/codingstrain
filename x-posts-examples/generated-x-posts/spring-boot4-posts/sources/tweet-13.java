# ❌ Boot 3
management:
  tracing:
    enabled: true

# ✅ Boot 4
management:
  tracing:
    export:
      enabled: true
    sampling:
      probability: 0.1

// Custom auto-config:
// @ConditionalOnEnabledTracingExport
@Configuration(proxyBeanMethods = false)
class MyTracingConfig { }
