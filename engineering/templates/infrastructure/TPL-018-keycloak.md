# Template ID: TPL-018

## Nombre
Keycloak Template

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
Definir la plantilla de documentación para Keycloak en la infraestructura.

## Alcance
- Estructura de la plantilla Keycloak.
- Archivos y convenciones mínimas.
- Reglas de uso.

## Estructura requerida
```
keycloak/
├── README.md
├── config/
├── scripts/
└── realms/
```

## Reglas clave
- No incluir configuración real de realms definitivos.
- Documentar la estrategia de realms, clientes y roles.
- Mantener la plantilla alineada con el modelo de seguridad.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
