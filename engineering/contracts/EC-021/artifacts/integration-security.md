# Integration Security

JWT/TenantContext es obligatorio para las APIs. El tenant se obtiene del claim `tenant_id` o `tenantId`; las consultas SQL incluyen el tenant. Permisos específicos del realm aún requieren validación de integración con Keycloak.
