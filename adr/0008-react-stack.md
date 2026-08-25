# ADR 0008: Stack Frontend Moderno (React + Radix + Tailwind)

## Estado
Aceptado

## Contexto
El frontend debe ser altamente interactivo, accesible y con un diseño consistente y moderno ("Futurista/Moderno").

## Problema
¿Qué tecnologías y librerías utilizar para construir una interfaz de usuario escalable y de alta calidad?

## Alternativas Consideradas
- **Angular/Vue**: Excelentes frameworks, pero el ecosistema React es más amplio y flexible para este proyecto.
- **Bootstrap/Material UI**: Estilos más genéricos y difíciles de personalizar para una estética futurista única.

## Decisión
Utilizar React con TypeScript, Tailwind CSS para estilos rápidos y flexibles, y Radix UI para componentes primitivos accesibles y sin estilo.

## Consecuencias
- **Positivas**: Alta velocidad de desarrollo UI, accesibilidad nativa, diseño totalmente personalizado, excelente experiencia de desarrollador.
- **Negativas**: Requiere configuración inicial de componentes de diseño sobre los primitivos de Radix.

## Impacto Futuro
Permite crear un sistema de diseño propio y evolucionar la interfaz con facilidad sin dependencias pesadas de estilos pre-definidos.
