# Inventory Lifecycle

Estados operacionales:

`AVAILABLE` -> `RESERVED` -> `ALLOCATED` -> `PICKED` -> `PACKED` -> `FULFILLED`

Transiciones alternativas:

- `RESERVED` -> `RELEASED` por cancelacion o expiracion;
- `ALLOCATED` -> `RELEASED` antes de picking;
- cualquier stock operativo -> `ADJUSTED` mediante ajuste autorizado;
- stock afectado -> `DAMAGED`;
- stock devuelto -> `RETURNED` y luego inspeccion.

`FULFILLED`, `DAMAGED` y `RETURNED` no se reabren sin una operacion explicita de ajuste o devolucion.
