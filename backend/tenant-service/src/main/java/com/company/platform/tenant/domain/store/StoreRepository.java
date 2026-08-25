package com.company.platform.tenant.domain.store;

import java.util.List; import java.util.UUID;

public interface StoreRepository { Store save(Store store); List<Store> findByTenantId(UUID tenantId); }
