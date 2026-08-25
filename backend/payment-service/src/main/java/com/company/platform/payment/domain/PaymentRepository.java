package com.company.platform.payment.domain;

import java.util.*;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findByTenantIdAndId(UUID tenantId, UUID id);
    Optional<Payment> findByTenantIdAndReference(UUID tenantId, String reference);
    List<Payment> findByTenantIdAndOrderId(UUID tenantId, UUID orderId);
    List<Payment> findByTenantIdAndCustomerId(UUID tenantId, UUID customerId);
    List<Payment> findByTenantId(UUID tenantId);
}
