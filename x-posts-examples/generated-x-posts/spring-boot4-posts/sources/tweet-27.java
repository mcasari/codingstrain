# application.yml
management:
  health:
    ssl:
      certificate-validity-warning-threshold: 14d
  endpoint:
    health:
      show-details: always

// Health JSON shape (simplified):
// {
//   "status": "UP",
//   "components": {
//     "ssl": {
//       "status": "UP",
//       "details": { "expiringChains": [ … ] }
//     }
//   }
// }
