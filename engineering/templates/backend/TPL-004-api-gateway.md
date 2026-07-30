# Template ID: TPL-004

## Nombre
API Gateway

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
Definir la plantilla oficial para el API Gateway del backend.

## Alcance
- Estructura del proyecto gateway.
- Archivos mínimos obligatorios.
- Documentación requerida.

## Estructura requerida
```
api-gateway/
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
- Servicio de enrutamiento y seguridad.
- No contener lógica de negocio de dominio.
- Documentar los contratos y rutas por API.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
