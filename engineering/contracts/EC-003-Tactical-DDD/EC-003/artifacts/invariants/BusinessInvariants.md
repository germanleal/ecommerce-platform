# Business Invariants Catalogue

## 1. Aggregate Invariants
### AI-ORD-01: Order Immutability
- **Contexto**: Order Fulfillment.
- **Regla**: Una vez que un pedido ha sido pagado, sus items y precios no pueden ser modificados.
- **Riesgo**: Pérdida de integridad financiera y desbalance en inventario.
- **Consecuencia**: Invalidación de la transacción.

### AI-INV-01: No Negative Stock
- **Contexto**: Inventory.
- **Regla**: El stock disponible no puede ser menor a cero (a menos que el tenant habilite explícitamente sobreventa).
- **Riesgo**: Incumplimiento de pedidos y mala experiencia de cliente.

## 2. Context Invariants
### CI-ST-01: Subdomain Uniqueness
- **Contexto**: Store Operations.
- **Regla**: No pueden existir dos tiendas con el mismo subdominio dentro de la plataforma, independientemente del Tenant.

## 3. Cross Context Rules
### CCR-MT-01: Global Tenant Aisolation
- **Regla**: Ninguna operación de base de datos o lógica de negocio puede exponer datos de un `tenant_id` a otro.
- **Consecuencia de incumplimiento**: Brecha de seguridad crítica y violación de contrato SaaS.
