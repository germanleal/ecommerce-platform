# Fulfillment Model

Fulfillment inicia despues de una reserva exitosa. Sus etapas son `STARTED`, `ALLOCATED`, `PICKED`, `PACKED`, `CANCELLED` y `FAILED`.

El operador puede confirmar picking y packing. La preparacion no genera despacho, guia, ruta ni tracking. Cada cambio conserva tenant, actor, timestamp y correlation id.
