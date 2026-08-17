# AI Security Model

Keycloak/JWT autentica; TenantContext aísla; AI Gateway autoriza por capacidad y clasificación de datos. Permisos futuros deberán distinguir `AI_READ`, `AI_EXECUTE`, `AI_CONTEXT_READ`, `AI_TOOL_EXECUTE` y `AI_AUDIT_READ`.

Todo uso será auditado con actor, tenant, provider lógico, tool, correlationId, clasificación y resultado. Se prohíbe enviar secretos, credenciales, tokens o datos fuera del scope autorizado.
