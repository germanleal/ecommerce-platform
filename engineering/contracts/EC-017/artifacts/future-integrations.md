# Future Integrations

## Payments

Consume `OrderConfirmedEvent` and publish payment authorization, capture, refund or failure facts. Payments owns charges and refunds; Order stores only references and payment status facts needed for lifecycle decisions.

## Inventory

Consume confirmed order items to request reservations. Inventory owns stock and reservation state.

## Logistics

Consume processing-ready orders and publish shipment/tracking facts. Logistics owns delivery execution.

## Notifications and Analytics

Subscribe to order lifecycle events. They do not mutate the Order aggregate.

These integrations are explicitly future scope for later contracts and are not implemented in EC-017 Part 1.

