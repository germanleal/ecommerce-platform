# Template ID: TPL-002

## Nombre
Java Library

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
Definir la plantilla para librerías Java compartidas que no contienen dominio específico.

## Alcance
- Estructura de librería Java reusable.
- Archivos básicos obligatorios.
- Separación clara entre API y tests.

## Estructura requerida
```
library-name/
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
- No incluir dominio de microservicio.
- No incluir reglas de negocio.
- Mantener dependencias limpias y documentadas.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
