# Tenant Context Design

`TenantContextHolder` usa `ThreadLocal`, permite `setTenant`, `getTenant` y `clear`, y el filtro limpia el contexto en un bloque `finally` al terminar cada request.
