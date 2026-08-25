package com.company.platform.marketplace.domain.store;

import java.util.*;

public interface StoreRepository { Store save(Store store); Optional<Store> findByTenantIdAndId(UUID tenantId,UUID id); }
