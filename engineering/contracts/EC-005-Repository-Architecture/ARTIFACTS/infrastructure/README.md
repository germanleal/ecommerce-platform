# EC-005 Parte 2: Infrastructure Repository Structure

Este documento define la estructura oficial de `infrastructure/`.

## Estructura Raíz de Infrastructure

El directorio `infrastructure/` debe contener toda la infraestructura de la plataforma.

Estructura mínima requerida:

```
infrastructure/
├── docker/
├── compose/
├── postgres/
├── kafka/
├── keycloak/
├── grafana/
├── prometheus/
├── loki/
├── tempo/
├── nginx/
└── certificates/
```

### Principios

- Todo lo relacionado con infraestructura reside aquí.
- No incluir código de aplicación ni microservicios.
- No mezclar plantillas de infraestructura con scripts de desarrollo.

## Contenido típico

- `docker/`: recursos y ejemplos de Dockerfiles.
- `compose/`: orquestación local de servicios.
- `postgres/`, `kafka/`, `keycloak/`: configuraciones relevantes de servicios.
- `grafana/`, `prometheus/`, `loki/`, `tempo/`: observabilidad.
- `nginx/`: proxy y routing de borde.
- `certificates/`: certificados TLS y configuración de seguridad.
