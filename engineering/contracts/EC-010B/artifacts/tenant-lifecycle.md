# Tenant lifecycle

Transiciones implementadas:

`CREATED -> ACTIVE -> SUSPENDED -> ACTIVE`

`CREATED|ACTIVE|SUSPENDED -> DEACTIVATED`

Las transiciones inválidas lanzan `IllegalStateException` y no se persisten.
