# 05. Requerimientos No Funcionales

## Escalabilidad
- RNF-01: Arquitectura de Microservicios para escalamiento horizontal independiente.
- RNF-02: Uso de Kafka para desacoplamiento y procesamiento asíncrono.

## Disponibilidad
- RNF-03: Alta disponibilidad mediante redundancia de servicios.
- RNF-04: Estrategias de resiliencia (Circuit Breakers, Retries).

## Seguridad
- RNF-05: Autenticación centralizada mediante OIDC (Keycloak).
- RNF-06: Aislamiento estricto de datos entre Tenants.
- RNF-07: Cifrado de datos sensibles en reposo y tránsito.

## Performance
- RNF-08: Tiempos de respuesta optimizados mediante caché (Redis) y queries eficientes.
- RNF-09: Optimización de assets frontend y carga diferida.

## Mantenibilidad
- RNF-10: Uso estricto de Arquitectura Hexagonal y DDD.
- RNF-11: Documentación automatizada de APIs (OpenAPI/Swagger).
- RNF-12: Estándares de código consistentes y revisión automática.

## Observabilidad
- RNF-13: Centralización de Logs, Métricas y Trazas.
- RNF-14: Health Checks en todos los servicios.
