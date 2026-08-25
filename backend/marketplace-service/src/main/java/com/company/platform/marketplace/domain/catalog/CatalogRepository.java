package com.company.platform.marketplace.domain.catalog;

import java.util.*;

public interface CatalogRepository { Catalog save(Catalog catalog); Optional<Catalog> findByTenantIdAndId(UUID tenantId,UUID id); }
