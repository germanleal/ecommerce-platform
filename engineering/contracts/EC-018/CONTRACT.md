# Engineering Contract: EC-018 — Payments & Billing

- **ID**: EC-018
- **Parte**: 1/4 — Payment Domain Discovery & Billing Context
- **Estado**: DRAFT
- **Dependencias**: EC-014 Marketplace Storefront, EC-016 Commerce Core, EC-017 Order Management

## Alcance

Diseñar el dominio Payments & Billing con separación clara entre procesamiento de pagos y gestión financiera posterior al pago.

EC-018 Parte 1 define el contexto de Payment y el contexto de Billing, el lenguaje ubicuo, el modelo de agregados, el ciclo de vida, la abstracción de gateways, el modelo de reembolsos, el modelo de facturación y la seguridad.

## Restricciones

- No implementar código.
- No diseñar APIs REST ejecutables.
- No definir persistencia física ni esquemas de base de datos.
- No integrar Stripe, Mercado Pago, PayPal, Transbank, bancos ni sistemas de facturación electrónica.
- No implementar frontend.
- No modificar Order, Commerce ni Marketplace.
- No almacenar información sensible sin justificación.

## Definition of Done

- Payment Context definido.
- Billing Context definido y separado de Payment.
- Límites claros con Order y con los contextos de Commerce/Marketplace.
- Lifecycle de pagos documentado.
- Aggregate Payment identificado y delimitado.
- PaymentAttempt identificado.
- Gateway Abstraction definida.
- Modelo de reembolso definido.
- Evento de dominio identificado.
- Modelo multi-tenant definido.
- Seguridad documentada.
- Artefactos obligatorios completos.

## Artefactos

- `artifacts/payment-domain-vision.md`
- `artifacts/payment-boundaries.md`
- `artifacts/payment-lifecycle.md`
- `artifacts/payment-glossary.md`
- `artifacts/payment-domain-model.md`
- `artifacts/billing-model.md`
- `artifacts/refund-model.md`
- `artifacts/gateway-abstraction.md`
- `artifacts/payment-events.md`
- `artifacts/payment-security-model.md`
