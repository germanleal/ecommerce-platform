package com.company.platform.payment.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public final class Payment {
    private final UUID id, tenantId, orderId, customerId;
    private final String paymentReference;
    private final Currency currency;
    private final BigDecimal amount;
    private final PaymentMethod paymentMethod;
    private final Instant createdAt;
    private PaymentStatus status;
    private Instant updatedAt;

    private Payment(UUID id, UUID tenantId, UUID orderId, UUID customerId, String reference,
                    PaymentStatus status, Currency currency, BigDecimal amount, PaymentMethod method,
                    Instant createdAt, Instant updatedAt) {
        if (tenantId == null || orderId == null || reference == null || reference.isBlank()
                || currency == null || method == null) throw new IllegalArgumentException("payment ownership required");
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("payment amount must be positive");
        this.id = id == null ? UUID.randomUUID() : id;
        this.tenantId = tenantId; this.orderId = orderId; this.customerId = customerId;
        this.paymentReference = reference; this.status = status == null ? PaymentStatus.CREATED : status;
        this.currency = currency; this.amount = amount; this.paymentMethod = method;
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
        this.updatedAt = updatedAt == null ? this.createdAt : updatedAt;
    }

    public static Payment create(UUID tenantId, UUID orderId, UUID customerId, String reference,
                                 Currency currency, BigDecimal amount, PaymentMethod method) {
        return new Payment(null, tenantId, orderId, customerId, reference, null, currency, amount, method, null, null);
    }

    public static Payment rehydrate(UUID id, UUID tenantId, UUID orderId, UUID customerId, String reference,
                                    PaymentStatus status, Currency currency, BigDecimal amount, PaymentMethod method,
                                    Instant createdAt, Instant updatedAt) {
        return new Payment(id, tenantId, orderId, customerId, reference, status, currency, amount, method, createdAt, updatedAt);
    }

    public void pending() { transition(PaymentStatus.PENDING, PaymentStatus.CREATED, PaymentStatus.FAILED, PaymentStatus.DECLINED); }
    public void processing() { transition(PaymentStatus.PROCESSING, PaymentStatus.PENDING, PaymentStatus.CREATED); }
    public void authorize() { transition(PaymentStatus.AUTHORIZED, PaymentStatus.PENDING, PaymentStatus.PROCESSING, PaymentStatus.CREATED); }
    public void capture() { transition(PaymentStatus.CAPTURED, PaymentStatus.AUTHORIZED, PaymentStatus.PENDING); }
    public void paid() { transition(PaymentStatus.PAID, PaymentStatus.PROCESSING, PaymentStatus.AUTHORIZED, PaymentStatus.CAPTURED); }
    public void settle() { transition(PaymentStatus.SETTLED, PaymentStatus.CAPTURED); }
    public void fail() { transition(PaymentStatus.FAILED, PaymentStatus.PENDING, PaymentStatus.AUTHORIZED); }
    public void decline() { transition(PaymentStatus.DECLINED, PaymentStatus.PENDING, PaymentStatus.CREATED); }
    public void cancel() { transition(PaymentStatus.CANCELLED, PaymentStatus.AUTHORIZED, PaymentStatus.PENDING, PaymentStatus.CREATED); }
    public void refunded(boolean partial) { transition(partial ? PaymentStatus.PARTIALLY_REFUNDED : PaymentStatus.REFUNDED, PaymentStatus.CAPTURED, PaymentStatus.SETTLED, PaymentStatus.PARTIALLY_REFUNDED); }
    private void transition(PaymentStatus target, PaymentStatus... allowed) {
        for (PaymentStatus value : allowed) if (status == value) { status = target; updatedAt = Instant.now(); return; }
        throw new IllegalStateException("invalid payment transition: " + status + " -> " + target);
    }
    public UUID id(){return id;} public UUID tenantId(){return tenantId;} public UUID orderId(){return orderId;}
    public UUID customerId(){return customerId;} public String paymentReference(){return paymentReference;}
    public PaymentStatus status(){return status;} public Currency currency(){return currency;} public BigDecimal amount(){return amount;}
    public PaymentMethod paymentMethod(){return paymentMethod;} public Instant createdAt(){return createdAt;} public Instant updatedAt(){return updatedAt;}
}
