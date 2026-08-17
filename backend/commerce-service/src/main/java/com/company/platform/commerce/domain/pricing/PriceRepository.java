package com.company.platform.commerce.domain.pricing;
import java.util.*;
public interface PriceRepository { Price save(Price price); Optional<Price> findByTenantIdAndId(UUID tenantId,UUID id); List<Price> findAllByTenantIdAndProductId(UUID tenantId,UUID productId); Optional<Price> findActiveByTenantIdAndProductId(UUID tenantId,UUID productId); }
