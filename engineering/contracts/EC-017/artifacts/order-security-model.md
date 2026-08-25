# Order Security Model

All access is authenticated through Keycloak and resolved within the authenticated tenant context. Every query and command is tenant-scoped; client-supplied tenant identifiers are not trusted.

| Permission | Scope |
|---|---|
| `ORDER_CREATE` | Customer may create an order for self within the active tenant/store |
| `ORDER_READ` | Customer reads own orders; staff reads authorized store/tenant orders |
| `ORDER_UPDATE` | Authorized staff updates non-terminal operational data |
| `ORDER_CANCEL` | Customer or authorized staff cancels according to lifecycle policy |
| `ORDER_CONFIRM` | Authorized checkout/order workflow confirms a pending order |
| `ORDER_PROCESS` | Operational staff advances processing within scope |

Platform administrators retain operational authority but must preserve auditability. Customer Service cannot cross tenant boundaries. Every transition records actor, tenant, timestamp, reason and correlation ID.

