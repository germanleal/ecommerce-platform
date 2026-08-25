# EC-021 Handoff to EC-022

## Estado

EC-021 queda **NOT APPROVED** hasta resolver la deuda crítica de retry/DLQ, integración real, seguridad y pruebas.

## Capacidades previstas

- Integration Service
- Connector Framework
- Adapter Framework
- Synchronization Engine
- Retry Engine
- Dead Letter Queue
- Scheduler
- Auditoría

## Restricciones para EC-022

Enterprise Administration solo podrá consumir APIs y eventos públicos; no podrá modificar Integration, ni dominios transaccionales, y deberá respetar tenant isolation.
