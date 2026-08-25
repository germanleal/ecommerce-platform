# Template ID: TPL-015

## Nombre
Docker Template

## Versión
1.0.0

## Estado
Approved

## Autor
Architecture Governance Board

## Fecha de creación
2026-07-29

## Compatibilidad
EC-005, EC-006

## Engineering Contract de origen
EC-006

## Objetivo
Definir la plantilla de documentación para Docker en proyectos e infraestructura.

## Alcance
- Estructura de la plantilla Docker.
- Archivos y convenciones mínimas.
- Reglas de uso.

## Estructura requerida
```
docker/
├── README.md
├── Dockerfile
├── templates/
└── scripts/
```

## Reglas clave
- No incluir configuraciones definitivas de Docker Compose.
- Mantener el Dockerfile como plantilla estándar para servicios.
- Documentar variables de entorno y puertos.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
