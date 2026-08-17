# EC-004 Parte 3 — Flujos cross-domain

## Auditoría de implementación

| Flujo | Evidencia existente | Estado |
|---|---|---|
| Tenant → usuarios → dominios | `tenant-service` publica eventos de tenant; `identity-service` no contiene implementación Java | Bloqueado |
| Marketplace → Commerce | Commerce tiene eventos propios; Marketplace no tiene productor Kafka | Bloqueado |
| Commerce → Orders | Checkout publica eventos y Orders tiene adapter HTTP/Kafka | Parcial |
| Orders → Payments | Payments publica eventos; no existe consumidor Kafka de Orders | Bloqueado |
| Orders → Inventory | Inventory escucha sólo eventos de payment/fulfillment | Bloqueado |
| Inventory → Orders | No existe consumidor de Inventory en Orders | Bloqueado |
| Business events → Analytics | Analytics consume seis topics y persiste por tenant | Parcial; falta E2E |
| Enterprise integrations | Integration consume y republica eventos; adapters externos aún no validados | Parcial |
| Administration | Tiene modelos de tenant/usuario, pero no consumidor de eventos | Bloqueado |

La tabla distingue código presente de flujo realmente validado. No se declara integración por la mera existencia de clases.
