# EC-016 Handoff to EC-017

## Available

Marketplace integration foundation, Commerce Context, Sellable Products, Pricing, Commercial Rules and Shopping Cart foundations.

## Restrictions for EC-017

Order Management must consume Commerce events and pricing, not duplicate Pricing or modify Marketplace. It must respect tenant isolation, existing security and event contracts.

## Open gates

EC-016 is formally approved. EC-017 may consume the published Commerce event contracts and tenant-scoped APIs within the restrictions above.
