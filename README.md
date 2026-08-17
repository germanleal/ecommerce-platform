# SaaS Multi-Tenant Ecommerce Platform

MVP multi-tenant con Spring Boot, PostgreSQL, Kafka, Keycloak y React.

## Requisitos

- Docker Desktop con Docker Compose v2.
- Java 21 y Maven para ejecutar módulos backend localmente.
- Node.js 20+ y pnpm 9.15.4 para el frontend local.

## Inicio rápido

1. Copia `.env.example` a `.env` y sustituye únicamente contraseñas de desarrollo.
2. Ejecuta `docker compose up --build -d`.
3. Comprueba el estado con `docker compose ps`.
4. Abre el frontend en `http://localhost:3000` y Keycloak en `http://localhost:8080`.

El stack publica PostgreSQL (`5432`), Kafka (`9092`), Keycloak (`8080`), el frontend (`3000`) y los servicios backend en `8085` y `8090`–`8099`.

## Datos demo

`postgres-seed` carga de forma idempotente Demo Organization, `demo-tenant`, Demo Store, catálogo, categoría, producto activo y precio USD. Se ejecuta automáticamente tras las migraciones y puede repetirse con `docker compose run --rm postgres-seed`.

## Configuración

Las variables requeridas y sus valores seguros para desarrollo están en `.env.example`. No se versionan `.env`, tokens, claves privadas ni credenciales reales. Los servicios Docker validan el issuer público de Keycloak y obtienen claves JWK mediante la red interna.

## Identidad y frontend

El realm `platform` se importa desde `infrastructure/keycloak/realm-platform.json`. El frontend usa Authorization Code con PKCE mediante el cliente público `web-client`; no almacena secretos de cliente.

## Pruebas

Ejemplo de backend:

```powershell
mvn -f shared/contracts/pom.xml clean install -DskipTests
mvn -f shared/security/pom.xml clean install -DskipTests
mvn -f backend/tenant-service/pom.xml clean verify
```

Frontend:

```powershell
pnpm --dir frontend/marketplace install
pnpm --dir frontend/marketplace test
pnpm --dir frontend/marketplace build
```

## Operación local

Los health checks se exponen en `/actuator/health`. Para detener conservando datos: `docker compose down`. Para un entorno nuevo, elimina explícitamente los volúmenes Docker y vuelve a ejecutar el inicio rápido.
