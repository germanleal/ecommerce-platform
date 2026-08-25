# 15. Guías de Testing

## Pirámide de Pruebas
1. **Unit Tests (70%)**: Foco en la lógica de dominio y casos de uso. Rápidos y aislados.
2. **Integration Tests (20%)**: Validación de adaptadores de infraestructura (BBDD, Mensajería) usando Testcontainers.
3. **End-to-End Tests (10%)**: Flujos críticos de usuario desde el frontend al backend.

## Estándares
- **JUnit 5** y **Mockito** para Java.
- **Jest** y **React Testing Library** para React.
- **AssertJ** para aserciones fluidas en Java.

## Cobertura
- Objetivo mínimo de cobertura del 80% en lógica de negocio.
- La cobertura no es un fin, sino un indicador de confianza.

## Test Data Management
- Uso de Object Mothers o Data Builders para generar datos de prueba consistentes.
- Aislamiento de datos entre tests.
