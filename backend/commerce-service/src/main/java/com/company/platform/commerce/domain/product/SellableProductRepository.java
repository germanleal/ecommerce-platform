package com.company.platform.commerce.domain.product;
import java.util.*;
public interface SellableProductRepository { SellableProduct save(SellableProduct product); Optional<SellableProduct> findByTenantIdAndId(UUID tenantId,UUID id); List<SellableProduct> findAllByTenantId(UUID tenantId); }
