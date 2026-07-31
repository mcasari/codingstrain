# application-prod.yml
logging:
  console:
    enabled: false
  file:
    name: /var/log/app/application.log
  level:
    root: INFO
    com.example: DEBUG

# Local profile keeps console on (default true)
