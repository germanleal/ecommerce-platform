# Enterprise Integration Ubiquitous Language

| Concepto | Definición | Regla |
|---|---|---|
| Connector | Configuración versionada de un sistema externo | Aislado por tenant y provider |
| Adapter | Implementación de un port para un proveedor | No filtra SDK al dominio |
| Integration | Flujo entre plataforma y sistema externo | Tiene lifecycle y auditoría |
| Synchronization | Ejecución de intercambio de datos | Idempotente y trazable |
| Mapping | Correspondencia de campos | Versionado junto al contrato |
| Payload | Datos transportados por mensaje | Mínimo, validado y sin secretos |
| Transformation | Conversión entre modelos | Vive en adapter/application boundary |
| Endpoint | Destino lógico de un adapter | No es un acoplamiento de dominio |
| Retry | Reintento de una operación recuperable | Tiene backoff y límite |
| Dead Letter Queue | Destino de mensajes no procesables | Requiere diagnóstico y replay controlado |
| Integration Event | Evento del lifecycle de integración | Incluye tenant y correlationId |
| External System | Sistema fuera de la plataforma | Nunca se trata como bounded context propio |
| Integration Audit | Registro de acciones y resultados | Inmutable y tenant-scoped |

## Clasificación

Connector governance y synchronization son core del contexto; adapters específicos son supporting; Kafka, HTTP, secret provider y DLQ son capacidades genéricas.

## Actores

Platform Administrator gobierna la plataforma; Integration Administrator configura conectores; External System participa como contraparte; ERP/CRM/Storage/Messaging son tipos de proveedores; API Consumer y API Provider intercambian contratos públicos.
