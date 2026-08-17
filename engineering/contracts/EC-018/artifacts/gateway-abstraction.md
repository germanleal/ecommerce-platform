# Payment Gateway Abstraction

## Propósito

Definir una capa de abstracción que permita integrar múltiples proveedores sin acoplar el dominio Payment a un proveedor específico.

## Principio

El Payment Context depende de una interfaz de Gateway, no de adaptadores concretos.

## Concepto

- `PaymentGateway`: interfaz de dominio.
- `PaymentProviderAdapter`: implementación concreta para cada proveedor.
- `GatewayRequest` / `GatewayResponse`: objetos que normalizan la comunicación.

## Operaciones clave

- `authorize(PaymentRequest) -> PaymentResponse`
- `capture(CaptureRequest) -> CaptureResponse`
- `refund(RefundRequest) -> RefundResponse`
- `cancel(CancelRequest) -> CancelResponse`
- `queryStatus(StatusRequest) -> StatusResponse`

## Modelo de implementación

```
PaymentGateway
  ↓
  StripeAdapter
  MercadoPagoAdapter
  TransbankAdapter
  PayPalAdapter
  AdyenAdapter
```

## Reglas

- Payment no debe conocer la clase concreta del adaptador.
- El dominio opera con la interfaz `PaymentGateway`.
- Cada adaptador mapea las operaciones estándar del dominio a la API del proveedor.
- El dominio persiste solo referencias y resultados genéricos del gateway, no detalles internos propietarios.
- Los adaptadores deben transformar estados de proveedor en estados del dominio (`AUTHORIZED`, `DECLINED`, `FAILED`, `CAPTURED`, `REFUNDED`).

## Extensibilidad

- Nuevos proveedores se agregan implementando `PaymentGateway`.
- La configuración de proveedores se gestiona fuera del dominio, en la capa de infraestructura.
- El dominio puede seleccionar un adaptador por `PaymentMethod`, `tenantId` o política.

## Ejemplo conceptual

- `PaymentGateway gateway = gatewayFactory.for(tenantId, paymentMethod)`
- `gateway.authorize(paymentRequest)`
- `gateway.capture(captureRequest)`
- `gateway.refund(refundRequest)`

## Observaciones

- Este diseño evita dependencias directas con Stripe, Mercado Pago, Transbank o PayPal.
- Permite un único flujo de negocio en Payment con múltiples proveedores interchangeables.
