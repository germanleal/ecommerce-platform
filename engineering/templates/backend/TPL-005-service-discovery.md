# Template ID: TPL-005

## Nombre
Service Discovery

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
Definir la plantilla para el servicio de descubrimiento de servicios.

## Alcance
- Estructura del proyecto de service discovery.
- Archivos mínimos obligatorios.
- Documentación de responsabilidad.

## Estructura requerida
```
service-discovery/
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

## Principios
- Mantener servicio enfocado en descubrimiento.
- No mezclar con lógica de negocio.
- Documentar la integración con Spring Cloud.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
