# Order Lifecycle

```text
DRAFT -> PENDING_CONFIRMATION -> CONFIRMED -> PROCESSING -> COMPLETED
                    |                 |
                    v                 v
                CANCELLED          CANCELLED

DRAFT/PENDING_CONFIRMATION -> EXPIRED
PENDING_CONFIRMATION -> REJECTED
```

## Transitions

| From | Event/action | To | Rule |
|---|---|---|---|
| DRAFT | submit | PENDING_CONFIRMATION | Snapshot is complete and at least one item exists |
| PENDING_CONFIRMATION | confirm | CONFIRMED | Confirmation preconditions are satisfied |
| PENDING_CONFIRMATION | reject | REJECTED | Order cannot be commercially confirmed |
| DRAFT/PENDING_CONFIRMATION | expire | EXPIRED | Confirmation window elapsed |
| DRAFT/PENDING_CONFIRMATION/CONFIRMED | cancel | CANCELLED | Cancellation policy permits it |
| CONFIRMED | begin processing | PROCESSING | Order accepted for downstream fulfillment |
| PROCESSING | complete | COMPLETED | Downstream completion is reported |

Transitions not listed are prohibited. `COMPLETED`, `CANCELLED`, `REJECTED` and `EXPIRED` are terminal states. No price or item mutation is allowed after confirmation.

