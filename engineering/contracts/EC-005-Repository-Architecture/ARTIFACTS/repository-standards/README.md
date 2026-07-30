# Repository Standards

Este documento describe los estándares obligatorios de organización, consistencia, independencia y mantenimiento para el repositorio.

## Estándares obligatorios

- Una única responsabilidad por directorio.
- Una única responsabilidad por proyecto.
- Una única responsabilidad por módulo.
- Ningún directorio podrá existir sin un propósito claramente documentado.

## Consistencia

- Todos los microservicios deben compartir exactamente la misma estructura.
- Todos los proyectos React deben compartir exactamente la misma estructura.
- Todas las librerías compartidas deben seguir exactamente la misma organización.

## Independencia

- Cada proyecto debe poder evolucionar de manera independiente.
- No debe existir dependencia directa entre microservicios.
- La comunicación se realiza exclusivamente mediante contratos definidos por la arquitectura.

## Escalabilidad

- La incorporación de un nuevo microservicio requiere sólo:
  - creación del nuevo proyecto;
  - registro documental;
  - actualización de los artefactos de arquitectura.
- Nunca debe reorganizarse el repositorio para añadir un nuevo servicio.

## Mantenibilidad

- Toda carpeta debe contener un único propósito.
- Evitar estructuras ambiguas.
- Ejemplos prohibidos: `misc/`, `others/`, `helpers/`, `temp/`, `new/`, `test2/`, `backup/`, `old/`.
