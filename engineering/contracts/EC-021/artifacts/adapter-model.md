# Adapter Model

Un Port es una interfaz propia de la plataforma: `InboundEventPort`, `OutboundCommandPort`, `ExternalQueryPort` y `AuditPort`. Un Adapter implementa un port para un proveedor concreto.

Transformers y mappers convierten el modelo canónico interno al contrato externo y viceversa. El dominio nunca importa SDKs de proveedores. La serialización, autenticación y códigos de error quedan encapsulados en el adapter.
