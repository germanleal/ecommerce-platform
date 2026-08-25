# Administration Security Review

JWT/TenantContext están configurados en backend y el cliente puede enviar Bearer token. **Bloqueador:** permisos por ruta, guards frontend, Keycloak real y pruebas de dos tenants no están validados.
