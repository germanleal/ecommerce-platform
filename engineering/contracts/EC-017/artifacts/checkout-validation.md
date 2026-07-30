# Checkout Validation

Automated coverage includes empty-cart rejection, domain lifecycle invariants and tenant-scoped repository behavior. `mvn -q test` passes for `backend/order-service`.

Runtime integration requires EC-016 to expose its existing cart-completion contract at `POST /commerce/carts/{id}/complete`; the endpoint is configured as an external Commerce boundary and is not implemented or modified by Order Management.

