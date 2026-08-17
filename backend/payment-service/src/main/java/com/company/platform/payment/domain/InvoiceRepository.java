package com.company.platform.payment.domain;

import java.util.*;

public interface InvoiceRepository {
    Invoice save(Invoice invoice);
    Optional<Invoice> findByTenantIdAndId(UUID tenantId, UUID id);
    Optional<Invoice> findByTenantIdAndPaymentId(UUID tenantId, UUID paymentId);
    List<Invoice> findByTenantIdAndOrderId(UUID tenantId, UUID orderId);
}
