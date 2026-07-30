# Template ID: TPL-014

## Nombre
Shared Clients

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
Definir la plantilla para clientes reutilizables compartidos.

## Alcance
- Estructura de librería para clientes HTTP/SDK.
- Archivos mínimos obligatorios.
- Documentación de integraciones.

## Estructura requerida
```
shared-clients/
├── src/
├── tests/
├── docs/
├── scripts/
├── package.json
├── README.md
├── CHANGELOG.md
├── LICENSE
├── .gitignore
├── .editorconfig
└── .gitattributes
```

## Reglas clave
- No incluir lógica de negocio.
- Documentar la API de los clientes.
- Mantener los contratos de integración claros.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
