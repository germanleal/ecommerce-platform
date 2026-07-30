# Commerce Glossary

| Término | Definición | Regla |
|---|---|---|
| Commerce | Contexto que determina vendibilidad y reglas comerciales. | Siempre tenant-aware. |
| Sellable Product | Producto de Marketplace habilitado comercialmente. | Requiere oferta válida. |
| Offer | Propuesta comercial de un producto. | Pertenece a un tenant/store. |
| Price | Monto monetario vigente de una oferta. | Requiere moneda y vigencia. |
| Currency | Moneda del precio. | Obligatoria y explícita. |
| Discount | Reducción aplicada bajo condiciones. | Debe ser trazable. |
| Promotion | Regla temporal o contextual de beneficio. | No modifica el dominio Marketplace. |
| Availability | Estado comercial del producto. | `AVAILABLE`, `UNAVAILABLE`, `DISCONTINUED`. |
| Cart | Intención de compra agrupada. | Futuro módulo de Commerce. |
| Customer | Actor que explora y expresa intención de compra. | No administra identidad IAM. |
