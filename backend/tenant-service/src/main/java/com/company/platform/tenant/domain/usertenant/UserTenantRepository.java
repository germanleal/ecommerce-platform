package com.company.platform.tenant.domain.usertenant;

import java.util.Optional; import java.util.UUID; import java.util.List;

public interface UserTenantRepository { UserTenant save(UserTenant relation); Optional<UserTenant> findByUserIdAndTenantId(UUID userId, UUID tenantId); List<UserTenant> findByTenantId(UUID tenantId); void remove(UUID userId, UUID tenantId); }
