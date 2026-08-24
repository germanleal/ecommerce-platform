package com.company.platform.order.infrastructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

class SecurityConfigurationTest {
    @Test
    void realmRolesAreAvailableWithAndWithoutSpringRolePrefix() {
        Jwt jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("sub", "customer")
            .claim("realm_access", Map.of("roles", List.of("ORDER_CREATE")))
            .build();

        var authentication = new SecurityConfiguration().jwtAuthenticationConverter().convert(jwt);
        var authorities = authentication.getAuthorities().stream().map(Object::toString).toList();

        assertTrue(authorities.contains("ORDER_CREATE"));
        assertTrue(authorities.contains("ROLE_ORDER_CREATE"));
    }
}
