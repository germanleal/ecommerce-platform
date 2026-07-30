# 08. Guías de Desarrollo

## General
- Seguir principios SOLID y DRY.
- Nombramiento descriptivo en inglés (Código) y documentación en español.
- Commits siguiendo la convención de Conventional Commits.

## Backend (Hexagonal)
- **Domain**: Solo POJOs, Value Objects, Entities y Domain Services. Sin dependencias de frameworks.
- **Application**: Use Cases e interfaces de Output Ports.
- **Infrastructure**: Implementaciones de adaptadores (BBDD, Mensajería, APIs externas).
- **API**: Controladores REST y DTOs.

## Frontend (Feature-Based)
- Organizar por `features`. Cada feature contiene sus componentes, hooks, servicios y tipos.
- Uso mandatorio de TypeScript para tipado estricto.
- Componentes visuales basados en Radix UI y Tailwind.

## Control de Versiones
- Rama `main` protegida.
- Feature branches: `feat/nombre-tarea`.
- Pull Requests con revisión obligatoria.
