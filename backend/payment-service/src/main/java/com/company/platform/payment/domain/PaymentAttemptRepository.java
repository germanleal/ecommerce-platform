package com.company.platform.payment.domain;

import java.util.*;

public interface PaymentAttemptRepository {
    PaymentAttempt save(PaymentAttempt attempt);
    int nextAttemptNumber(UUID tenantId, UUID paymentId);
    List<PaymentAttempt> findByTenantIdAndPaymentId(UUID tenantId, UUID paymentId);
}
