# Engineering Contract: EC-002-Strategic-DDD

## Información General
- **ID**: EC-002
- **Nombre**: Strategic Domain Driven Design
- **Versión**: 1.0.0
- **Estado**: COMPLETADO
- **Autor**: Architecture Governance Board (AGB)
- **Fecha**: 2026-07-29
- **Dependencias**: EC-001

## Objetivo
Definir los límites del dominio y las relaciones estratégicas entre los distintos contextos de la plataforma.

## Alcance
- **Incluye**: Bounded Contexts, Subdomains, Context Map, Ubiquitous Language.
- **No incluye**: Diseño táctico (Agregados, Entidades).

## Entradas Obligatorias
- EC-001: Product Discovery.
- Business Capability Map.

## Actividades
1. **Identificación de Subdominios**: Clasificar Core, Supporting y Generic.
2. **Definición de Bounded Contexts**: Establecer límites de lenguaje y responsabilidad.
3. **Context Mapping**: Definir relaciones (OHS, ACL, Partnership).

## Artefactos de Salida
- `docs/domain/07-subdomains.md`
- `docs/domain/08-bounded-contexts.md`
- `docs/domain/09-context-map.md`
- `docs/domain/10-ubiquitous-language.md`

## Definition of Done
- [x] Mapa de Contextos (Context Map) sin ambigüedades.
- [x] Lenguaje Ubicuo definido para cada contexto.
- [x] Subdominios Core identificados.

## Artefactos para el siguiente contrato
- Context Map.
- Definición de Bounded Contexts.
