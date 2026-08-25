# Reglas para Agentes de IA

Como agente de IA que colabora en este proyecto, debes seguir estas reglas estrictas para garantizar la calidad y consistencia:

## 1. Contexto Arquitectónico
- Siempre asume que el proyecto utiliza **Arquitectura Hexagonal** y **DDD**.
- No propongas soluciones que violen el aislamiento del dominio.
- Respeta los límites de los Bounded Contexts.

## 2. Estándares de Código
- Sigue las guías de codificación definidas en `engineering-handbook/20-coding-standards.md`.
- No generes código sin tests unitarios.
- Usa nombres descriptivos en inglés.

## 3. Seguridad
- Nunca propongas soluciones que omitan la validación de `tenant_id`.
- No almacenes secretos en el código.
- Usa UUIDs en lugar de IDs secuenciales en las interfaces externas.

## 4. Comunicación
- Justifica tus decisiones basándote en los ADRs existentes.
- Si una tarea es ambigua, solicita aclaración antes de implementar.
- Documenta el código generado de forma clara.

## 5. Prohibiciones
- No utilices librerías que no estén en el stack oficial sin previa consulta.
- No ignores los errores de linter o compilación.
- No modifiques archivos de migración de base de datos ya existentes.
