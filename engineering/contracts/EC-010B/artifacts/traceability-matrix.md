# EC-010B Traceability Matrix

| Requirement | Architecture decision | Implementation | Test / evidence | Documentation |
|---|---|---|---|---|
| Tenant identity | UUID Aggregate Root | `Tenant`, `TenantId` | `TenantDomainTest` | `tenant-domain-model.md` |
| Store ownership | Store requires tenantId | `Store`, FK | Domain test + schema | `multi-tenant-strategy.md` |
| Tenant isolation | Shared schema + discriminator | `stores.tenant_id`, context | Integration pending | `tenant-data-policy.md` |
| Context safety | Thread-local request context | `TenantContextHolder`, filter | Unit/compile pass | `tenant-context-runtime.md` |
| Context authorization | User + role + permission + tenant | `TenantAccessValidator` | Integration pending | `tenant-authorization-design.md` |
