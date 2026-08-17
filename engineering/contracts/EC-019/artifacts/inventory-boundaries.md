# Inventory Boundaries

Order publica hechos de compra y cancelacion. Payments publica hechos de confirmacion financiera. Inventory consume ambos contratos y decide si puede reservar stock.

Inventory publica hechos de reserva y fulfillment para consumidores downstream. No escribe Order, Payment, Commerce ni Marketplace, ni accede a sus tablas.

```text
Order --events--> Inventory <--> Warehouse
Payments --events-> Inventory --> Fulfillment
```

Fulfillment usa referencias a Order, Payment y productos; no es propietario de esos aggregates.
