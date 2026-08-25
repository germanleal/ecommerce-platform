# Synchronization Model

- **Inbound:** proveedor externo → adapter → evento/proyección interna.
- **Outbound:** evento interno → transformer → adapter → proveedor.
- **Batch/programada:** ejecución con ventana, cursor y checkpoint.
- **Event-driven:** ejecución disparada por evento público versionado.

Toda ejecución tiene `syncId`, `tenantId`, connector version, correlationId, estado, timestamps y resultado. Las operaciones deben ser idempotentes; una sincronización no puede escribir directamente en tablas de otro bounded context.
