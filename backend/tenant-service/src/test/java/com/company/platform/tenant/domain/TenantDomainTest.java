package com.company.platform.tenant.domain;

import com.company.platform.tenant.domain.store.Store;
import com.company.platform.tenant.domain.tenant.*;
import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class TenantDomainTest {
    @Test void tenantStartsCreatedAndCanActivate() {
        var tenant = Tenant.create(new TenantName("Demo"), new Slug("demo"), Map.of());
        assertEquals(TenantStatus.CREATED, tenant.status());
        tenant.activate();
        assertEquals(TenantStatus.ACTIVE, tenant.status());
    }
    @Test void slugRejectsUnsafeValues() { assertThrows(IllegalArgumentException.class, () -> new Slug("Demo Store!!!")); }
    @Test void storeRequiresTenant() { assertThrows(IllegalArgumentException.class, () -> Store.create(null, "Store", new Slug("store"), Map.of())); }
    @Test void userTenantRequiresIdentityAndTenant() { assertThrows(IllegalArgumentException.class, () -> new com.company.platform.tenant.domain.usertenant.UserTenant(null, UUID.randomUUID(), null, "ADMIN", "ACTIVE", null)); }
}
