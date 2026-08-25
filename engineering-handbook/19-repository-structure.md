# 19. Estructura del Repositorio

## Organización (Monorepo Conceptual)
El proyecto se organiza en un repositorio único para facilitar la consistencia y el desarrollo coordinado.

```
project-root/
├── docs/                   # Documentación general y diagramas.
├── architecture/           # Definiciones arquitectónicas profundas.
├── adr/                    # Architecture Decision Records.
├── engineering-handbook/   # Este manual de ingeniería.
├── backend/                # Microservicios Spring Boot.
├── frontend/               # Aplicación React.
├── shared/                 # Recursos compartidos (Contratos, Tipos, etc).
├── infrastructure/         # Configuración de servicios (PostgreSQL, Kafka).
├── docker/                 # Dockerfiles y recursos de imagen.
├── compose/                # Orquestación de contenedores.
├── scripts/                # Scripts de automatización (Setup, Deploy).
├── templates/              # Plantillas de código y documentos.
├── testing/                # Recursos globales de testing.
└── prompts/                # Prompts estructurados para agentes IA.
```

## Convenciones de Nombres
- Directorios: `kebab-case`.
- Archivos de Documentación: `kebab-case.md`.
- Microservicios: `ms-nombre-servicio`.
