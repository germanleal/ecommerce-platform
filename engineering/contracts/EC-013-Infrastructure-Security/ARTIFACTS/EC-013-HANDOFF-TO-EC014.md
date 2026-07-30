# EC-013 Handoff to EC-014

## Entregables disponibles

- Infraestructura Docker Compose y redes/volúmenes.
- DevSecOps y gestión de secretos.
- Observabilidad, health checks y runbooks.
- IAM de EC-010A.
- Tenant Context y aislamiento de EC-010B.

## Restricciones para EC-014

- Todo dominio debe ser tenant-aware.
- Toda API debe usar la seguridad existente.
- Toda entidad debe respetar DDD y arquitectura hexagonal.
- No crear autenticación paralela ni aceptar `tenantId` público como autoridad.
