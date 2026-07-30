# Template ID: TPL-017

## Nombre
Kafka Template

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
Definir la plantilla de documentación para Kafka en la infraestructura.

## Alcance
- Estructura de la plantilla Kafka.
- Archivos y convenciones mínimas.
- Reglas de uso.

## Estructura requerida
```
kafka/
├── README.md
├── config/
├── scripts/
└── topics/
```

## Reglas clave
- No definir topics físicos detallados.
- Documentar patrones de eventos y ownership.
- Mantener la plantilla alineada con la arquitectura de eventos.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
