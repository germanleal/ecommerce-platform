# Template ID: TPL-012

## Nombre
TypeScript Shared Library

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
Definir la plantilla para librerías TypeScript compartidas.

## Alcance
- Estructura de librería TypeScript reutilizable.
- Archivos básicos obligatorios.
- Documentación mínima.

## Estructura requerida
```
typescript-library/
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
- No incluir dominio compartido.
- Separar claramente API de implementación.
- Documentar los tipos y contratos expuestos.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
