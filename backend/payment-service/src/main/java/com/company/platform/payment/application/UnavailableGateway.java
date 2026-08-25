package com.company.platform.payment.application;

import com.company.platform.payment.domain.*;
import org.springframework.stereotype.Component;

/** Deterministic local provider for the MVP. Select success, failure or timeout with PAYMENT_PROVIDER_MODE. */
@Component public final class UnavailableGateway implements PaymentGateway {
 private String mode(){return System.getenv().getOrDefault("PAYMENT_PROVIDER_MODE","success").toLowerCase(java.util.Locale.ROOT);}
 private GatewayResult result(Payment p){return switch(mode()){case "failure" -> new GatewayResult(false,"FAKE",null,"PAYMENT_PROVIDER_REJECTED");case "timeout" -> new GatewayResult(false,"FAKE",null,"PAYMENT_PROVIDER_TIMEOUT");default -> new GatewayResult(true,"FAKE","fake-"+p.id(),null);};}
 public GatewayResult authorize(Payment p){return result(p);} public GatewayResult capture(Payment p){return result(p);} public GatewayResult cancel(Payment p){return result(p);} public GatewayResult refund(Refund r){return new GatewayResult(true,"FAKE","fake-refund-"+r.id(),null);} public GatewayResult getPaymentStatus(Payment p){return result(p);}
}
