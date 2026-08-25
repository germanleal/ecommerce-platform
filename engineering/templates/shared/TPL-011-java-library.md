# Template ID: TPL-011

## Nombre
Java Shared Library

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
Definir la plantilla para librerías Java compartidas sin lógica de dominio.

## Alcance
- Estructura de librería Java reutilizable.
- Archivos básicos obligatorios.
- Documentación mínima.

## Estructura requerida
```
java-library/
├── src/
│   ├── main/java/
│   └── test/java/
├── docs/
├── tests/
├── scripts/
├── .mvn/
├── pom.xml
├── README.md
├── CHANGELOG.md
├── LICENSE
├── .gitignore
├── .editorconfig
└── .gitattributes
```

## Reglas clave
- No incluir dominio compartido.
- No incluir reglas de negocio.
- Documentar dependencias y responsabilidades.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
