# application.yml
server:
  tomcat:
    resource:
      cache-max-size: 10MB
  compression:
    enabled: true
    mime-types: text/html,text/xml,text/plain,application/json

spring:
  web:
    resources:
      chain:
        cache: true
