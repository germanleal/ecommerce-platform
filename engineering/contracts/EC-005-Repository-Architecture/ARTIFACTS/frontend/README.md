# EC-005 Parte 2: Frontend Repository Structure

Este documento define la estructura oficial del frontend del monorepo.

## Estructura Raíz del Frontend

El directorio `frontend/` debe contener todas las aplicaciones React y el design system.

Estructura mínima requerida:

```
frontend/
├── marketplace/
├── backoffice/
├── tenant-admin/
├── shared-ui/
└── design-system/
```

### Observaciones

- `marketplace/`, `backoffice/` y `tenant-admin/` son aplicaciones React independientes.
- `shared-ui/` contiene componentes, hooks y utilidades frontend reutilizables específicas del frontend.
- `design-system/` es el proyecto independiente de interfaz reutilizable.

## Convenciones de nombres

- Usar `kebab-case` para nombres de carpetas y archivos.
- Cada aplicación debe tener un `package.json` y archivos raíz estándar.
- No mezclar lógica de negocio con el design system.
