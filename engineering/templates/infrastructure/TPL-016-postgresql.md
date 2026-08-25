# Template ID: TPL-016

## Nombre
PostgreSQL Template

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
Definir la plantilla de documentación para PostgreSQL en la infraestructura.

## Alcance
- Estructura de la plantilla PostgreSQL.
- Archivos y convenciones mínimas.
- Reglas de uso.

## Estructura requerida
```
postgres/
├── README.md
├── init/
├── scripts/
└── config/
```

## Reglas clave
- No incluir tablas SQL definitivas.
- Documentar variables de entorno y esquemas conceptuales.
- Mantener la plantilla independiente del servicio.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
