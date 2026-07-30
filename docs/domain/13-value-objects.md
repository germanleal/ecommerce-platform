# 13. Value Objects

## Objetivo
Definir los Objetos de Valor (Value Objects) que representan conceptos descriptivos del dominio sin identidad propia, definidos por sus atributos e inmutables.

## 1. Value Objects Transversales

### Money
- **Descripción**: Representa una cantidad monetaria con su moneda.
- **Propiedades**: `amount` (BigDecimal), `currency` (ISO Code).
- **Reglas**: No se pueden sumar dineros de distintas monedas sin conversión.
- **¿Por qué VO?**: $10 USD es igual a cualquier otro $10 USD; no importa su identidad, solo su valor.

### Address
- **Descripción**: Representa una ubicación física para envíos o facturación.
- **Propiedades**: `street`, `city`, `state`, `zipCode`, `country`.
- **Reglas**: Debe ser una dirección válida y completa.
- **¿Por qué VO?**: Dos direcciones con los mismos datos son la misma ubicación.

---

## 2. Contexto: Store Operations

### Price
- **Descripción**: El valor comercial de un producto.
- **Propiedades**: `basePrice` (Money), `taxPercentage`, `discountAmount`.
- **Reglas**: El precio final se calcula sumando impuestos y restando descuentos.

### ProductAttribute
- **Descripción**: Características de una variante (Color, Talla, Material).
- **Propiedades**: `name`, `value`.

---

## 3. Contexto: Provisioning

### TenantContact
- **Descripción**: Información de contacto del responsable del Tenant.
- **Propiedades**: `fullName`, `email`, `phone`.

## Resumen de Value Objects

| Value Object | Atributos Clave | Contexto |
| :--- | :--- | :--- |
| Money | amount, currency | Global |
| Address | street, city, country | Fulfillment / Provisioning |
| Price | base, tax, discount | Catalog |
| OrderStatus | code, description | Fulfillment |
| SKU | code | Catalog / Inventory |

## Referencias Cruzadas
- Ver [11. Aggregate Model](file:///D:/personales/development/ecommerce-platform/docs/domain/11-aggregate-model.md) para ver dónde se utilizan estos objetos.
