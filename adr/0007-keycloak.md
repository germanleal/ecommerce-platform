# ADR 0007: Keycloak para Gestión de Identidad y Acceso

## Estado
Aceptado

## Contexto
La seguridad es crítica en un entorno multi-tenant. Desarrollar un sistema de autenticación propio es costoso y propenso a errores.

## Problema
¿Cómo gestionar la identidad de los usuarios y la autenticación de forma segura y estandarizada?

## Alternativas Consideradas
- **Custom Auth Service**: Alto costo de desarrollo y mantenimiento.
- **Auth0 / Okta**: Excelentes pero son servicios SaaS propietarios con costos asociados por usuario.
- **Keycloak**: Solución Open Source líder, altamente personalizable y compatible con estándares.

## Decisión
Utilizar Keycloak para centralizar la autenticación, gestión de identidades y emisión de tokens OIDC/JWT.

## Consecuencias
- **Positivas**: Cumplimiento de estándares, seguridad probada, soporte para múltiples protocolos, ahorro de tiempo de desarrollo.
- **Negativas**: Necesidad de gestionar y desplegar una instancia de Keycloak.

## Impacto Futuro
Facilita la integración con proveedores de identidad externos (Social Login, SAML) y garantiza un manejo profesional de la seguridad.
