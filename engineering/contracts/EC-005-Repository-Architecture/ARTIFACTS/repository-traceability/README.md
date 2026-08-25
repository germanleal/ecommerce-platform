# Repository Traceability Matrix

Este documento describe la trazabilidad requerida entre componentes técnicos y origen de negocio.

## Objetivo

Mantener una matriz que permita navegar desde cualquier componente técnico hasta su origen.

## Ruta de trazabilidad

```
Engineering Contract
↓
Microservicio
↓
Proyecto
↓
Módulo
↓
Paquete
↓
Responsabilidad
↓
Bounded Context
↓
Capability
↓
Objetivo del negocio
```

## Reglas

- No debe existir ningún componente sin trazabilidad.
- Cada service, módulo y paquete debe poder relacionarse con un bounded context y capability.
- Los Engineering Contracts deben ser la fuente principal de trazabilidad.
- Las decisiones importantes deben registrarse en ADR.

## Ejemplo

- `EC-004` → `catalog-service` → `backend/catalog-service` → `application` → `com.company.catalog.application` → `ProductCatalogManagement` → `Store Operations` → `Dynamic Catalog Engine` → `Permitir catálogos altamente personalizables por cada tenant`
