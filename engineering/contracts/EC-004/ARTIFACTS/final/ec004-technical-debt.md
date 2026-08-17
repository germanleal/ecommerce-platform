# EC-004 Technical Debt

| Prioridad | Deuda | Impacto | Recomendación |
|---|---|---|---|
| Critical | Identity Service sin implementación verificable | Impide flujo de identidad y seguridad E2E | Implementar IAM, eventos y pruebas JWT |
| Critical | Saga Orders/Payments/Inventory incompleta | Estados distribuidos pueden quedar inconsistentes | Implementar orquestador, compensaciones y timeouts |
| Critical | Falta E2E con Kafka/PostgreSQL reales | No se puede certificar integración | Incorporar Testcontainers y pipeline reproducible |
| High | Marketplace no publica eventos | Commerce no puede reaccionar por contrato | Añadir producer versionado y consumer test |
| High | Falta DLQ Kafka uniforme | Fallos pueden perderse o bloquear particiones | Configurar recoverer, topics DLQ, métricas y replay |
| High | Deduplicación no uniforme | Reintentos pueden duplicar efectos | Persistir `(tenant_id,event_id)` en cada consumidor |
| Medium | Envelopes y naming incompatibles | Evolución de contratos riesgosa | Migrar todos a shared contracts versionados |
| Low | Documentación de estados dispersa | Dificulta governance | Mantener un registro único de evidencias |
