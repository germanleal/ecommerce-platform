package com.company.platform.payment.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.UUID;

public final class Invoice {
    private final UUID id, tenantId, paymentId, orderId, customerId;
    private final String invoiceNumber; private final Currency currency;
    private final BigDecimal subtotal, total; private InvoiceStatus status;
    private final Instant createdAt; private Instant updatedAt, issuedAt;
    private Invoice(UUID id, UUID tenantId, String number, UUID paymentId, UUID orderId, UUID customerId,
                    Currency currency, BigDecimal subtotal, BigDecimal total, InvoiceStatus status,
                    Instant issuedAt, Instant createdAt, Instant updatedAt) {
        if (tenantId == null || number == null || number.isBlank() || paymentId == null || orderId == null || currency == null)
            throw new IllegalArgumentException("invalid invoice");
        if (subtotal == null || subtotal.signum() < 0 || total == null || total.signum() < 0 || total.compareTo(subtotal) < 0)
            throw new IllegalArgumentException("invalid invoice amount");
        this.id=id==null?UUID.randomUUID():id; this.tenantId=tenantId; this.invoiceNumber=number; this.paymentId=paymentId; this.orderId=orderId;
        this.customerId=customerId; this.currency=currency; this.subtotal=subtotal; this.total=total;
        this.status=status==null?InvoiceStatus.DRAFT:status; this.issuedAt=issuedAt; this.createdAt=createdAt==null?Instant.now():createdAt;
        this.updatedAt=updatedAt==null?this.createdAt:updatedAt;
    }
    public static Invoice create(UUID tenantId,String number,UUID paymentId,UUID orderId,UUID customerId,Currency currency,BigDecimal subtotal,BigDecimal total){return new Invoice(null,tenantId,number,paymentId,orderId,customerId,currency,subtotal,total,null,null,null,null);}
    public static Invoice rehydrate(UUID id,UUID tenantId,String number,UUID paymentId,UUID orderId,UUID customerId,Currency currency,BigDecimal subtotal,BigDecimal total,InvoiceStatus status,Instant issuedAt,Instant createdAt,Instant updatedAt){return new Invoice(id,tenantId,number,paymentId,orderId,customerId,currency,subtotal,total,status,issuedAt,createdAt,updatedAt);}
    public void pending(){if(status!=InvoiceStatus.DRAFT)throw new IllegalStateException("invalid invoice transition");status=InvoiceStatus.PENDING;touch();}
    public void issue(){if(status!=InvoiceStatus.PENDING)throw new IllegalStateException("invoice requires pending status");status=InvoiceStatus.ISSUED;issuedAt=Instant.now();touch();}
    public void cancel(){if(status!=InvoiceStatus.DRAFT&&status!=InvoiceStatus.PENDING)throw new IllegalStateException("invalid invoice cancellation");status=InvoiceStatus.CANCELLED;touch();}
    private void touch(){updatedAt=Instant.now();}
    public UUID id(){return id;} public UUID tenantId(){return tenantId;} public String invoiceNumber(){return invoiceNumber;} public UUID paymentId(){return paymentId;} public UUID orderId(){return orderId;} public UUID customerId(){return customerId;} public Currency currency(){return currency;} public BigDecimal subtotal(){return subtotal;} public BigDecimal total(){return total;} public InvoiceStatus status(){return status;} public Instant issuedAt(){return issuedAt;} public Instant createdAt(){return createdAt;} public Instant updatedAt(){return updatedAt;}
}
