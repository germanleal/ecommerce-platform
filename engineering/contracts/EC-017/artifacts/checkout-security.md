# Checkout Security

Checkout resolves the tenant from the authenticated Keycloak JWT. The Commerce adapter propagates the tenant context, and the orchestrator rejects a cart whose tenant differs from the authenticated tenant. Customer identity and cart ownership are validated before order creation.

