package com.company.platform.order.application;

import java.math.BigDecimal;
import java.util.UUID;

public interface CommerceProductPort {
 ProductSnapshot resolve(UUID tenantId, UUID productId);
 record ProductSnapshot(UUID productId, UUID tenantId, UUID storeId, String sku, String name, BigDecimal amount, String currency) {}
}
