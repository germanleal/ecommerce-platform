# EC-005 Parte 2: Backend Repository Structure

Este documento define la estructura oficial del backend del monorepo, alineada con los principios de EC-005.

## Estructura Raíz del Backend

El directorio `backend/` debe contener todos los microservicios Java Spring Boot del sistema.

Estructura mínima requerida:

```
backend/
├── api-gateway/
├── service-discovery/
├── config-server/
├── identity-service/
├── tenant-service/
├── marketplace-service/
├── catalog-service/
├── product-service/
├── inventory-service/
├── pricing-service/
├── promotion-service/
├── cart-service/
├── checkout-service/
├── order-service/
├── payment-service/
├── shipping-service/
├── notification-service/
├── customer-service/
├── review-service/
├── media-service/
├── search-service/
├── audit-service/
├── administration-service/
└── monitoring-service/
```

### Principios aplicados

- Cada microservicio es independiente y reconocido por su nombre.
- No se permiten excepciones: todos los servicios deben existir con la misma estructura de directorio interno.
- No compartir código de dominio entre servicios.
- La dependencia cruzada se evita mediante contratos y APIs/eventos.

## Tipo de Proyecto

- Java + Spring Boot + Spring Cloud.
- Cada microservicio es un proyecto separado con su propio `pom.xml`.

## Notas

- Los servicios como `api-gateway`, `service-discovery` y `config-server` son infraestructura de plataforma y deben residir bajo `backend/` junto con los servicios de negocio.
- `monitoring-service` se considera un servicio de soporte de backend para métricas y observabilidad.
