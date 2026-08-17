package com.company.platform.payment.application;

import com.company.platform.payment.domain.Payment;
import com.company.platform.payment.domain.Refund;

public interface PaymentGateway {
    GatewayResult authorize(Payment payment);
    GatewayResult capture(Payment payment);
    GatewayResult cancel(Payment payment);
    GatewayResult refund(Refund refund);
    GatewayResult getPaymentStatus(Payment payment);
    record GatewayResult(boolean successful, String provider, String providerReference, String failureReason) {
        public static GatewayResult unavailable(String provider) { return new GatewayResult(false, provider, null, "gateway not configured"); }
    }
}
