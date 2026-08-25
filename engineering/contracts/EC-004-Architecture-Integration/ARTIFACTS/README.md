# EC-004 Architecture Integration Artifacts

Este directorio contiene la documentación resultante de EC-004, basada en el modelo de dominio y la definición de bounded contexts de EC-002 y EC-003.

## Estructura

- `architecture/system-context`: contexto general del sistema y relaciones entre bounded contexts.
- `architecture/microservices`: definición de microservicios y responsabilidades.
- `architecture/deployment-diagrams`: topología de despliegue local y dependencias.
- `architecture/communication`: estrategia de comunicación síncrona y asíncrona.
- `architecture/kafka`: estrategia de eventos y ownership.
- `architecture/security`: modelo de seguridad general.
- `architecture/keycloak`: integración de Keycloak.
- `architecture/multitenancy`: arquitectura multi-tenant.
- `architecture/frontend`: arquitectura del frontend React.
- `architecture/observability`: estrategia de logs, métricas y trazas.
- `architecture/docker-compose`: arquitectura local con Docker Compose.

## Nota

Los artefactos son conceptuales. No se incluyen implementaciones de código, Dockerfiles, `docker-compose.yml` final, ni configuraciones de infraestructura.
