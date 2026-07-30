package com.company.platform.marketplace.domain.product;

import java.util.*;

public interface ProductRepository { Product save(Product product); Optional<Product> findByTenantIdAndId(UUID tenantId,UUID id); List<Product> findAllByTenantId(UUID tenantId); }
