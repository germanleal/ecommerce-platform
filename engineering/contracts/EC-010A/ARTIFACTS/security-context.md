# Security context

`identity-service` exposes `UserContext(userId, username, tenantId, clientId, roles)` from the authenticated JWT at `/api/v1/identity/me`. Internal REST propagation uses the original `Authorization` token plus correlation headers; event envelopes carry user, tenant and correlation metadata without passwords or tokens.
