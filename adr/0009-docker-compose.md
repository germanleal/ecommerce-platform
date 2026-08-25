# ADR 0009: Orquestación Local con Docker Compose

## Estado
Aceptado

## Contexto
El entorno de desarrollo debe ser fácil de configurar y idéntico entre todos los miembros del equipo.

## Problema
¿Cómo garantizar que todos los desarrolladores y agentes de IA trabajen sobre la misma infraestructura base?

## Alternativas Consideradas
- **Instalación Manual**: Propensa a errores y versiones inconsistentes.
- **Kubernetes Local (Minikube/Kind)**: Alta complejidad inicial.
- **Docker Compose**: Simple, eficiente y estándar para entornos de desarrollo.

## Decisión
Utilizar Docker Compose como la herramienta principal para levantar toda la infraestructura y microservicios en entornos locales.

## Consecuencias
- **Positivas**: Setup del proyecto con un solo comando, aislamiento de dependencias, portabilidad total.
- **Negativas**: Consumo de recursos locales (RAM/CPU) al levantar múltiples contenedores.

## Impacto Futuro
Facilita la transición a orquestadores de producción como Kubernetes al tener ya imágenes Docker y definiciones de red claras.
