# Guías de Desarrollo con IA

Estas guías definen cómo los desarrolladores humanos deben interactuar con las IAs para maximizar la productividad.

## 1. Proporcionar Contexto
- Siempre comparte los documentos relevantes del `engineering-handbook/` y los `adr/` al iniciar una tarea compleja.
- Utiliza los templates de prompts definidos en `templates/PROMPT_TEMPLATE.md`.

## 2. Revisión de Código IA
- El código generado por IA debe ser tratado con el mismo (o mayor) rigor que el código humano.
- Utiliza `templates/CODE_REVIEW_TEMPLATE.md` para las revisiones.

## 3. Iteración incremental
- Divide las tareas grandes en prompts pequeños y manejables.
- Valida cada paso antes de continuar al siguiente.

## 4. Mantenimiento de Prompts
- Si encuentras un prompt que funciona excepcionalmente bien, guárdalo en la carpeta `prompts/` para uso futuro.
- Actualiza estas reglas si detectas patrones de error recurrentes en la IA.
