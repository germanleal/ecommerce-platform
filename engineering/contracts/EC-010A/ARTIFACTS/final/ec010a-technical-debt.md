# EC-010A Technical Debt — Remediation

| Priority | Debt | Required closure |
|---|---|---|
| Critical | Shared security jar not consumed by every service | Add dependency and replace local converters in all services |
| Critical | PlatformEvent not adopted by all publishers/consumers | Migrate envelope and validate tenant/user/correlation |
| Critical | Security audit not persisted uniformly | Add shared audit sink and integration tests |
| High | Authorization Code and Refresh Token real test inputs absent | Run smoke with Keycloak provisioned test credentials |
| High | Two-tenant E2E absent | Add real Compose security suite |
