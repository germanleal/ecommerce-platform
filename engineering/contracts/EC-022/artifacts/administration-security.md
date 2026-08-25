# Security Review

Keycloak mantiene identidad y credenciales. El servicio valida JWT y requiere tenant claim. Los permisos específicos de cada endpoint deben mapearse al realm y probarse antes del cierre de producción.
