# Bounded Context Validation

| Contexto | Ownership observado | Resultado |
|---|---|---|
| Marketplace | productos y catálogo propios | Parcial: sin publicación de eventos |
| Commerce | pricing y carrito propios | Parcial |
| Orders | pedido propio | Parcial: sin listeners de workflow |
| Payments | pago e idempotencia HTTP propias | Parcial: sin listener de Orders |
| Inventory | inventario/reservas propias | Parcial |
| Analytics | read models propios, sin consultas externas detectadas | Parcial |
| Integrations | adapters y retry persistente | Parcial |
| Administration | tenant/usuario administrativos | Parcial: sin consumidores |
| Identity | sin implementación Java verificable | No conforme |
