package com.company.platform.order.domain;
import java.util.*;
public interface OrderRepository {Order save(Order order);Optional<Order> findByTenantIdAndId(UUID tenantId,UUID id);Optional<Order> findByTenantIdAndOrderNumber(UUID tenantId,String number);List<Order> findByTenantIdAndCustomerId(UUID tenantId,UUID customerId);List<Order> findByTenantIdAndStoreId(UUID tenantId,UUID storeId);default List<Order> findByTenantId(UUID tenantId,OrderStatus status){return List.of();}}
