# 11. Integración con Keycloak

## Responsabilidades
Keycloak es el responsable único de:
- Almacenamiento de identidades de usuario.
- Flujos de Login / Logout / Registro.
- Gestión de Sesiones.
- Emisión y validación de Tokens (JWT).

## Configuración de Realms
- Se utilizará un Realm principal para la plataforma.
- Los usuarios se segmentan mediante Atributos de Usuario o Grupos para identificar su pertenencia a un Tenant.

## Flujos Soportados
- **Authorization Code Flow con PKCE**: Para la aplicación Frontend (React).
- **Client Credentials**: Para comunicación Service-to-Service si es necesario.

## Atributos Personalizados
Cada usuario tendrá un atributo `tenant_id` en su perfil de Keycloak, el cual será incluido en los Claims del token JWT generado.
