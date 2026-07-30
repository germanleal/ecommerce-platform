# Docker Compose Architecture

Este documento define la arquitectura local de Docker Compose para la plataforma, sin generar el artefacto final.

## Objetivo

- Definir qué servicios deben levantarse en local.
- Describir dependencias y orden de inicio.
- Alinear el entorno local con el modelo de arquitectura.

## Servicios esperados

- `postgres`: PostgreSQL compartido.
- `kafka`: Apache Kafka.
- `zookeeper`: Zookeeper para Kafka.
- `keycloak`: Keycloak para identidad y autorización.
- `api-gateway`: puerta de entrada unificada.
- `provisioning-service`
- `iam-service`
- `store-operations-service`
- `order-fulfillment-service`
- `marketplace-discovery-service`
- `payments-billing-service`
- `frontend`
- `observability`
  - `prometheus`
  - `jaeger`
  - `log-aggregator`

## Dependencias de arranque

1. PostgreSQL
2. Zookeeper
3. Kafka
4. Keycloak
5. Servicios backend
6. Frontend
7. Observabilidad

## Arquitectura de local

- Todos los servicios backend se conectan a PostgreSQL y publican/consumen eventos desde Kafka.
- Keycloak provee tokens para el frontend y los servicios.
- El API Gateway enruta peticiones del frontend a los microservicios.
- Observabilidad recopila métricas y trazas de todos los servicios.

## Notas

- El archivo `docker-compose.yml` no se crea todavía.
- No se definen variables de entorno exactas ni volúmenes específicos.
- La arquitectura local contempla servicios de observabilidad aunque su despliegue puede ser opcional inicialmente.
