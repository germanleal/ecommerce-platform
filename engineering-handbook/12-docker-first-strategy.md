# 12. Estrategia Docker First

## Principio
Toda la plataforma debe ser reproducible localmente con un solo comando:
`docker compose up -d`

## Estructura de Compose
- `docker-compose.yml`: Archivo principal que orquesta los servicios.
- `infrastructure/`: Contiene archivos de configuración para servicios base (PostgreSQL, Kafka, Keycloak).
- `docker/`: Contiene los Dockerfiles de los microservicios y la aplicación frontend.

## Beneficios
- Consistencia total entre entornos de desarrollo.
- Onboarding inmediato para nuevos desarrolladores y agentes de IA.
- Preparación nativa para despliegues en Kubernetes (Cloud Native).

## Requisitos
- Docker Desktop o Docker Engine instalado.
- Recursos mínimos recomendados: 16GB RAM, 4 CPUs.
