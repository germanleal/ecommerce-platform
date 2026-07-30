# Template ID: TPL-003

## Nombre
Config Server

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
Definir la plantilla para el servicio de configuración centralizada de Spring Cloud.

## Alcance
- Estructura del proyecto config server.
- Archivos requeridos.
- Documentación mínima.

## Estructura requerida
```
config-server/
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
- Centralizar configuración sin lógica de negocio.
- Mantener la plantilla alineada con el estándar de microservicios.
- Documentar las dependencias de Spring Cloud.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
