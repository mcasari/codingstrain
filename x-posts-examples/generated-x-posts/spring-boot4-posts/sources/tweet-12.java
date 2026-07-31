# ❌ Boot 3 — this key no longer exists
management:
  tracing:
    enabled: true
# annotation was: @ConditionalOnEnabledTracing

# ✅ Boot 4 — enable exporting traces
management:
  tracing:
    export:
      enabled: true
    sampling:
      probability: 0.1
# annotation is now: @ConditionalOnEnabledTracingExport
