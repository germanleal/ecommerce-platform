# ADR 0010: Estrategia Multi-Tenancy (Base de Datos Compartida)

## Estado
Aceptado

## Contexto
El sistema debe soportar múltiples organizaciones independientes de forma eficiente.

## Problema
¿Cuál es el modelo de aislamiento de datos más adecuado para las etapas iniciales y medias del proyecto?

## Alternativas Consideradas
- **Base de Datos por Tenant**: Máximo aislamiento pero alta complejidad operacional y costo.
- **Esquema por Tenant**: Buen balance pero difícil de escalar a miles de tenants en Postgres.
- **Tabla Compartida (Discriminador)**: Más simple de implementar y escalar horizontalmente.

## Decisión
Adoptar el modelo de **Base de Datos Compartida con Esquema Compartido**, utilizando una columna `tenant_id` en todas las tablas de negocio.

## Consecuencias
- **Positivas**: Simplicidad operacional, facilidad para realizar agregaciones globales, menor consumo de recursos.
- **Negativas**: Riesgo de fuga de datos si no se implementan filtros estrictos en la capa de aplicación.

## Impacto Futuro
Permite un crecimiento rápido con costos controlados. Se pueden migrar tenants grandes a bases de datos dedicadas en el futuro si es necesario.
