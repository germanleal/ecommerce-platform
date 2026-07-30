package com.company.platform.order.domain;
import java.util.*;
public interface OrderItemRepository {List<OrderItem> findByTenantIdAndOrderId(UUID tenantId,UUID orderId);}
