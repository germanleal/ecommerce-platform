# 10. Estrategia Multi-Tenancy

## Modelo de Datos
Se utilizará un enfoque de **Base de Datos Compartida con Esquema Compartido**.
- Cada tabla relevante para el negocio contendrá una columna `tenant_id`.
- El aislamiento se garantiza mediante filtros a nivel de aplicación (Hibernate Filters o mecanismos similares).

## Contexto de Tenant
- El `tenant_id` se extrae del token JWT en cada petición.
- Se utiliza un `TenantContext` basado en `ThreadLocal` en el backend para propagar la identidad del tenant durante la ejecución del hilo.

## Aislamiento de Recursos
- **Imágenes/Archivos**: Almacenados en carpetas o buckets separados por prefijo de tenant.
- **Configuración**: Cada tenant tiene su propio set de configuraciones (Moneda, Idioma, Branding).

## Escalabilidad del Modelo
Este modelo permite un crecimiento rápido y eficiente en costos, manteniendo la complejidad operacional baja en las etapas iniciales.
