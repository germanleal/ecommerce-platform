package com.company.platform.tenant.domain.usertenant;

import java.util.Optional; import java.util.UUID;

public interface UserTenantRepository { UserTenant save(UserTenant relation); Optional<UserTenant> findByUserIdAndTenantId(UUID userId, UUID tenantId); }
