# Disaster Recovery Plan

## Servicios

Recrear Compose desde el repositorio, restaurar volúmenes y verificar health/readiness en el orden PostgreSQL → Keycloak/Kafka → aplicaciones.

## Datos

Restaurar PostgreSQL de negocio, PostgreSQL IAM, Kafka y configuraciones desde backups verificados.

## Validación

Ejecutar migraciones Flyway, comprobar conectividad, autenticación, contexto tenant y eventos operativos. Documentar rollback si falla una validación.
