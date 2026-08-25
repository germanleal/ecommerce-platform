# Domain Policy: SuspendStoreOnTenantSuspension

## Objetivo
Garantizar la coherencia administrativa: si una empresa deja de pagar o es suspendida, todas sus tiendas deben dejar de operar inmediatamente.

## Contexto
Relación entre **Provisioning** y **Store Operations**.

## Evento Disparador
`TenantSuspended`.

## Resultado Esperado
Todas las `Store` vinculadas al `tenant_id` cambian su estado a `MAINTENANCE` o `CLOSED` según la gravedad.

## Restricciones
- La política debe ser capaz de procesar múltiples tiendas de forma asíncrona pero consistente.
