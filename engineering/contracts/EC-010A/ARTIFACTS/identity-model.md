# EC-010A — Identity model

Keycloak owns `User`, `Role`, `Client`, `Realm` and `Session`. `identity-service` owns the platform integration boundary and exposes normalized user claims; it does not store passwords or duplicate Keycloak users.

Tenant ownership remains in `tenant-service`/EC-010B. The `tenant_id` claim is consumed but not created by this service.
