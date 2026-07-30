# ADR 0006: PostgreSQL como Base de Datos Principal

## Estado
Aceptado

## Contexto
La plataforma requiere una persistencia relacional robusta con soporte para transacciones ACID y capacidades avanzadas de consulta.

## Problema
¿Cuál es el motor de base de datos más adecuado para los microservicios?

## Alternativas Consideradas
- **MySQL**: Muy popular pero con menos funcionalidades avanzadas en comparación con Postgres.
- **PostgreSQL**: Potente, open source y con excelente soporte para tipos de datos complejos (JSONB) y extensiones.

## Decisión
Utilizar PostgreSQL como base de datos relacional estándar para todos los microservicios.

## Consecuencias
- **Positivas**: Integridad de datos garantizada, soporte para consultas complejas, excelente integración con Spring Data JPA.
- **Negativas**: Ninguna significativa para el caso de uso.

## Impacto Futuro
Facilita el manejo de datos multi-tenant y permite el uso de capacidades NoSQL mediante JSONB si es necesario.
