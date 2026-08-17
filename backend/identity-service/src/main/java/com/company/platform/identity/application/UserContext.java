package com.company.platform.identity.application;

import java.util.List;

public record UserContext(String userId, String username, String tenantId, String clientId, List<String> roles) {
    public UserContext { roles = roles == null ? List.of() : List.copyOf(roles); }
}
