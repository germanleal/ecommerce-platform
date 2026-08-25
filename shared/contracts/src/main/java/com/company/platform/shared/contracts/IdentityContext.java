package com.company.platform.shared.contracts;

import java.util.List;

/** Identity data allowed to cross a service boundary; never contains credentials or tokens. */
public record IdentityContext(String userId, String username, List<String> roles, List<String> permissions,
                              String tenantId, String clientId, String correlationId) {
    public IdentityContext { roles = roles == null ? List.of() : List.copyOf(roles); permissions = permissions == null ? List.of() : List.copyOf(permissions); }
}
