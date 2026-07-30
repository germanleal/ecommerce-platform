# Template ID: TPL-010

## Nombre
Design System

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
Definir la plantilla para el proyecto independiente de design system.

## Alcance
- Estructura del design system.
- Carpetas mínimas obligatorias.
- Documentación mínima.

## Estructura requerida
```
design-system/
├── components/
├── tokens/
├── themes/
├── icons/
├── providers/
├── hooks/
├── animations/
├── stories/
└── playground/
```

## Reglas clave
- Todos los componentes deben extender Radix UI.
- No reemplazar Radix UI con componentes propios.
- Usar TailwindCSS como sistema de estilos base.
- Documentar qué componentes se exponen y cómo se consumen.

## Referencias
- EC-005: Repository Architecture
- EC-006: Engineering Templates
