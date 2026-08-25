# Feature Flag Model

Una flag tiene `key`, descripción, estado, entorno, tenant scope, reglas de targeting, porcentaje de rollout, versión y auditoría. El valor por defecto debe ser seguro; cambios requieren permiso, validación y registro. Una flag no debe contener secretos ni modificar reglas transaccionales sin contrato explícito.
