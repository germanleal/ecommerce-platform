# Tenant context propagation

REST boundary: `Authorization` is the credential, `X-Tenant-Id` is an auditable context hint, and `X-Correlation-Id` is the trace key. The server compares the tenant hint with `tenant_id`/`tenantId` in the verified JWT.

Kafka boundary: event publishers must carry tenant, user and correlation metadata in the shared envelope; raw tokens and passwords are never propagated.
