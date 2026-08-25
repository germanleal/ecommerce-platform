# Checkout Flow

`CheckoutOrchestrator` executes: load active tenant-scoped cart, validate customer/status/items, query Commerce for each active price, validate product/price compatibility, build immutable order-item snapshots, create and submit Order, request cart completion, and publish checkout/order facts.

The orchestrator owns no pricing rules. It consumes Commerce responses through `CommerceCheckoutPort` and does not persist or recalculate Commerce data.

