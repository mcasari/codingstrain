# application.yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics
# -> GET /actuator/health
