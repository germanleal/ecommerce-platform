# Template ID: TPL-001

## Nombre
Spring Boot Microservice

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
Definir la plantilla oficial para microservicios Spring Boot con arquitectura hexagonal.

## Alcance
- Estructura de proyecto completa.
- Archivos básicos obligatorios.
- Capas `application`, `domain`, `infrastructure`, `bootstrap`, `shared`.
- Documentación de servicio mínimo.

## Estructura requerida
```
service-name/
├── src/
├── docs/
├── tests/
├── docker/
├── scripts/
├── ADR/
├── .mvn/
├── pom.xml
├── README.md
├── CHANGELOG.md
├── LICENSE
├── .gitignore
├── .editorconfig
└── .gitattributes
```

## Estructura de `src/main/java`
```
src/main/java/
application/
domain/
infrastructure/
bootstrap/
shared/
```

## Reglas clave
- No incluir lógica de negocio en `shared/`.
- No usar componentes tecnológicos dentro de `domain/`.
- Todo archivo debe ser interpretado como parte del template, no código productivo.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
