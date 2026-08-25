# EC-005 Parte 2: Shared Repository Structure

Este documento define la estructura oficial de `shared/` para librerías y contratos compartidos.

## Estructura Raíz de Shared

El directorio `shared/` debe contener recursos reutilizables que no sean lógica de negocio de microservicios.

Estructura mínima requerida:

```
shared/
├── java-libraries/
├── frontend-libraries/
├── contracts/
├── schemas/
└── clients/
```

### Principios

- No incluir dominio ni casos de uso.
- No usar `shared/` como vía para compartir lógica de negocio entre microservicios.
- Los recursos deben ser reutilizables y neutrales a un servicio específico.

## Contenido típico

- `java-libraries/`: utilidades y bibliotecas internas Java que no contienen reglas de dominio.
- `frontend-libraries/`: componentes e utilidades frontend reutilizables.
- `contracts/`: definiciones de contratos, DTOs y modelos compartidos.
- `schemas/`: esquemas de API, eventos y validación.
- `clients/`: clientes HTTP genéricos y adaptadores compartidos.
