package com.company.platform.payment.domain;

import java.util.*;

public interface RefundRepository {
    Refund save(Refund refund);
    Optional<Refund> findByTenantIdAndId(UUID tenantId, UUID id);
    Optional<Refund> findByTenantIdAndReference(UUID tenantId, String reference);
    List<Refund> findByTenantIdAndPaymentId(UUID tenantId, UUID paymentId);
}
