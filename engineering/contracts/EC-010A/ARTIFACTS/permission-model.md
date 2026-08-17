# Permission model

Granular authorities include `PRODUCT_CREATE`, `PRODUCT_READ`, `PRODUCT_UPDATE`, `ORDER_CREATE`, `ORDER_READ`, `ORDER_CONFIRM`, `ORDER_CANCEL`, `PAYMENT_PROCESS`, `USER_CREATE`, `USER_READ`, `USER_UPDATE` and `TENANT_READ`.

Authorities can arrive in JWT `permissions` or Keycloak `realm_access.roles`. Resource ownership and `tenant_id` checks remain mandatory after role authorization.
