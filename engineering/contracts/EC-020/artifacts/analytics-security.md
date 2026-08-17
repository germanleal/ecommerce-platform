# EC-020 Parte 2 — Security

El servicio valida JWT mediante el issuer configurable de Keycloak y exige autenticación para todas las rutas salvo health check. `SecurityTenantContextProvider` obtiene `tenant_id`/`tenantId` del token y todas las queries lo aplican como filtro.

Pendiente de validación de integración: claims/permisos concretos del realm de Keycloak y pruebas HTTP con dos tenants.
