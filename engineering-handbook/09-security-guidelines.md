# 09. Guías de Seguridad

## Autenticación
- Gestionada por Keycloak mediante protocolos estándar (OIDC/OAuth2).
- Uso de JWT para la propagación de identidad entre servicios.

## Autorización
- **RBAC (Role-Based Access Control)**: Roles definidos en Keycloak (Admin, TenantAdmin, StoreManager, Customer).
- **Fine-Grained Authorization**: Lógica de permisos de negocio implementada en los microservicios.

## Protección de Datos
- No exponer IDs secuenciales en las APIs (Usar UUIDs).
- Validación estricta de inputs (Zod en Frontend, Bean Validation en Backend).
- Sanitización de salidas para prevenir XSS.

## Comunicaciones
- Todo el tráfico debe ser HTTPS (TLS 1.2+).
- Uso de API Gateway para centralizar políticas de seguridad.

## Multi-Tenancy Security
- Cada query a la base de datos debe filtrar por `tenant_id`.
- Los tokens JWT deben incluir el `tenant_id` para validación de contexto.
