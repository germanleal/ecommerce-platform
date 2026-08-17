# platform-shared-contracts

Módulo Java 21 con contratos mínimos y estables para comunicación entre bounded contexts.

Incluye `IntegrationEvent`, `EventMetadata`, `ApiError` y `PageRequest`. No contiene lógica de dominio ni dependencias de Spring, Kafka o persistencia; los adaptadores de cada servicio serializan estos tipos.

## Validación

```powershell
mvn clean test
```

El output de Maven se dirige a `build-output` para evitar bloqueos de permisos sobre `target`.

## Estado

Base implementada de EC-004. La aprobación de cada integración requiere pruebas de productor/consumidor y validación end-to-end.
