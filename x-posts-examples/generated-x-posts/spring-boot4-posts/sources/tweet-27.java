# ❌ Boot 3 — expiring certs used a special status
# ssl.status could be WILL_EXPIRE_SOON (confusing for probes)

# ✅ Boot 4 — stay UP, list chains that expire soon
management:
  health:
    ssl:
      certificate-validity-warning-threshold: 14d
  endpoint:
    health:
      show-details: always

# /actuator/health (ssl component)
# {
#   "status": "UP",
#   "details": {
#     "expiringChains": [ "server-cert…" ]
#   }
# }
