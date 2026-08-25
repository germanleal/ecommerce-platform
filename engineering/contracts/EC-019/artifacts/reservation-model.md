# Reservation Model

Una Reservation se crea cuando Inventory recibe una solicitud valida para una Order y Payment confirmados segun el contrato de eventos.

Estados: `REQUESTED`, `RESERVED`, `ALLOCATED`, `RELEASED`, `EXPIRED`, `CONSUMED`, `FAILED`.

Una operacion repetida con la misma clave y payload devuelve el mismo resultado. Una clave reutilizada con payload diferente se rechaza. La expiracion libera cantidades de forma atomica e idempotente.
