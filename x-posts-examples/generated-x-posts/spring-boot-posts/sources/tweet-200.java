# ✅ Default (Boot 3.4 / 4) — graceful is already on
# server.shutdown=graceful  (implicit)
spring:
  lifecycle:
    timeout-per-shutdown-phase: 30s  # max drain time

# ❌ Opt out — kill in-flight requests immediately
server:
  shutdown: immediate
