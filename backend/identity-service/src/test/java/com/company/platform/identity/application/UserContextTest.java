package com.company.platform.identity.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Test;

class UserContextTest {
    @Test void rolesAreImmutable() {
        var context = new UserContext("u", "user", "t", "client", List.of("USER"));
        assertEquals(List.of("USER"), context.roles());
    }
}
