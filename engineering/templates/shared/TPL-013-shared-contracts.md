# Template ID: TPL-013

## Nombre
Shared Contracts

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
Definir la plantilla para contratos compartidos entre servicios.

## Alcance
- Estructura de proyecto para contratos compartidos.
- Archivos mínimos obligatorios.
- Documentación de esquemas y DTOs.

## Estructura requerida
```
shared-contracts/
├── src/
├── docs/
├── tests/
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
- Contratos deben ser neutrales al dominio.
- No incluir lógica de negocio.
- Documentar versiones y compatibilidad.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
