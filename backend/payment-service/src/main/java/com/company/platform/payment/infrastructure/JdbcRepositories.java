package com.company.platform.payment.infrastructure;

import com.company.platform.payment.domain.*;
import java.sql.Timestamp; import java.time.Instant; import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate; import org.springframework.stereotype.Repository;

@Repository
class JdbcPaymentRepository implements PaymentRepository {
    private final JdbcTemplate jdbc; public JdbcPaymentRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    public Payment save(Payment p){jdbc.update("INSERT INTO payments(id,tenant_id,order_id,customer_id,payment_reference,status,currency,amount,payment_method,created_at,updated_at) VALUES(?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET status=?,updated_at=?",p.id(),p.tenantId(),p.orderId(),p.customerId(),p.paymentReference(),p.status().name(),p.currency().getCurrencyCode(),p.amount(),p.paymentMethod().name(),Timestamp.from(p.createdAt()),Timestamp.from(p.updatedAt()),p.status().name(),Timestamp.from(p.updatedAt()));return p;}
    private Payment map(java.sql.ResultSet r,int n)throws java.sql.SQLException{return Payment.rehydrate((UUID)r.getObject("id"),(UUID)r.getObject("tenant_id"),(UUID)r.getObject("order_id"),(UUID)r.getObject("customer_id"),r.getString("payment_reference"),PaymentStatus.valueOf(r.getString("status")),Currency.getInstance(r.getString("currency")),r.getBigDecimal("amount"),PaymentMethod.valueOf(r.getString("payment_method")),r.getTimestamp("created_at").toInstant(),r.getTimestamp("updated_at").toInstant());}
    public Optional<Payment> findByTenantIdAndId(UUID t,UUID id){return jdbc.query("SELECT * FROM payments WHERE tenant_id=? AND id=?",this::map,t,id).stream().findFirst();}
    public Optional<Payment> findByTenantIdAndReference(UUID t,String ref){return jdbc.query("SELECT * FROM payments WHERE tenant_id=? AND payment_reference=?",this::map,t,ref).stream().findFirst();}
    public List<Payment> findByTenantIdAndOrderId(UUID t,UUID order){return jdbc.query("SELECT * FROM payments WHERE tenant_id=? AND order_id=? ORDER BY created_at DESC",this::map,t,order);} public List<Payment> findByTenantIdAndCustomerId(UUID t,UUID customer){return jdbc.query("SELECT * FROM payments WHERE tenant_id=? AND customer_id=? ORDER BY created_at DESC",this::map,t,customer);} public List<Payment> findByTenantId(UUID t){return jdbc.query("SELECT * FROM payments WHERE tenant_id=? ORDER BY created_at DESC",this::map,t);}
}

@Repository
class JdbcPaymentAttemptRepository implements PaymentAttemptRepository {
    private final JdbcTemplate jdbc; JdbcPaymentAttemptRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    public PaymentAttempt save(PaymentAttempt a){jdbc.update("INSERT INTO payment_attempts(id,tenant_id,payment_id,provider,attempt_number,status,provider_reference,requested_at,completed_at,failure_reason) VALUES(?,?,?,?,?,?,?,?,?,?)",a.id(),a.tenantId(),a.paymentId(),a.provider(),a.attemptNumber(),a.status().name(),a.providerReference(),Timestamp.from(a.requestedAt()),a.completedAt()==null?null:Timestamp.from(a.completedAt()),a.failureReason());return a;}
    public int nextAttemptNumber(UUID t,UUID p){Integer value=jdbc.queryForObject("SELECT COALESCE(MAX(attempt_number),0)+1 FROM payment_attempts WHERE tenant_id=? AND payment_id=?",Integer.class,t,p);return value==null?1:value;}
    public List<PaymentAttempt> findByTenantIdAndPaymentId(UUID t,UUID p){return jdbc.query("SELECT * FROM payment_attempts WHERE tenant_id=? AND payment_id=? ORDER BY attempt_number",(r,n)->new PaymentAttempt((UUID)r.getObject("id"),(UUID)r.getObject("tenant_id"),(UUID)r.getObject("payment_id"),r.getString("provider"),r.getInt("attempt_number"),AttemptStatus.valueOf(r.getString("status")),r.getString("provider_reference"),r.getTimestamp("requested_at").toInstant(),r.getTimestamp("completed_at")==null?null:r.getTimestamp("completed_at").toInstant(),r.getString("failure_reason")),t,p);}
}

@Repository
class JdbcInvoiceRepository implements InvoiceRepository {
    private final JdbcTemplate jdbc; JdbcInvoiceRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    public Invoice save(Invoice i){jdbc.update("INSERT INTO invoices(id,tenant_id,invoice_number,payment_id,order_id,customer_id,currency,subtotal,total,status,issued_at,created_at,updated_at) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET status=?,issued_at=?,updated_at=?",i.id(),i.tenantId(),i.invoiceNumber(),i.paymentId(),i.orderId(),i.customerId(),i.currency().getCurrencyCode(),i.subtotal(),i.total(),i.status().name(),i.issuedAt()==null?null:Timestamp.from(i.issuedAt()),Timestamp.from(i.createdAt()),Timestamp.from(i.updatedAt()),i.status().name(),i.issuedAt()==null?null:Timestamp.from(i.issuedAt()),Timestamp.from(i.updatedAt()));return i;}
    private Invoice map(java.sql.ResultSet r,int n)throws java.sql.SQLException{return Invoice.rehydrate((UUID)r.getObject("id"),(UUID)r.getObject("tenant_id"),r.getString("invoice_number"),(UUID)r.getObject("payment_id"),(UUID)r.getObject("order_id"),(UUID)r.getObject("customer_id"),Currency.getInstance(r.getString("currency")),r.getBigDecimal("subtotal"),r.getBigDecimal("total"),InvoiceStatus.valueOf(r.getString("status")),r.getTimestamp("issued_at")==null?null:r.getTimestamp("issued_at").toInstant(),r.getTimestamp("created_at").toInstant(),r.getTimestamp("updated_at").toInstant());}
    public Optional<Invoice> findByTenantIdAndId(UUID t,UUID id){return jdbc.query("SELECT * FROM invoices WHERE tenant_id=? AND id=?",this::map,t,id).stream().findFirst();} public Optional<Invoice> findByTenantIdAndPaymentId(UUID t,UUID p){return jdbc.query("SELECT * FROM invoices WHERE tenant_id=? AND payment_id=?",this::map,t,p).stream().findFirst();} public List<Invoice> findByTenantIdAndOrderId(UUID t,UUID o){return jdbc.query("SELECT * FROM invoices WHERE tenant_id=? AND order_id=? ORDER BY created_at DESC",this::map,t,o);}
}

@Repository
class JdbcRefundRepository implements RefundRepository {
    private final JdbcTemplate jdbc; JdbcRefundRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    public Refund save(Refund r){jdbc.update("INSERT INTO refunds(id,tenant_id,payment_id,order_id,refund_reference,refund_amount,currency,reason,status,provider_reference,created_at,updated_at,processed_at) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET status=?,provider_reference=?,updated_at=?,processed_at=?",r.id(),r.tenantId(),r.paymentId(),r.orderId(),r.reference(),r.amount(),r.currency().getCurrencyCode(),r.reason(),r.status().name(),r.providerReference(),Timestamp.from(r.createdAt()),Timestamp.from(r.updatedAt()),r.processedAt()==null?null:Timestamp.from(r.processedAt()),r.status().name(),r.providerReference(),Timestamp.from(r.updatedAt()),r.processedAt()==null?null:Timestamp.from(r.processedAt()));return r;}
    private Refund map(java.sql.ResultSet r,int n)throws java.sql.SQLException{return Refund.rehydrate((UUID)r.getObject("id"),(UUID)r.getObject("tenant_id"),(UUID)r.getObject("payment_id"),(UUID)r.getObject("order_id"),r.getString("refund_reference"),r.getBigDecimal("refund_amount"),Currency.getInstance(r.getString("currency")),r.getString("reason"),RefundStatus.valueOf(r.getString("status")),r.getString("provider_reference"),r.getTimestamp("created_at").toInstant(),r.getTimestamp("updated_at").toInstant(),r.getTimestamp("processed_at")==null?null:r.getTimestamp("processed_at").toInstant());}
    public Optional<Refund> findByTenantIdAndId(UUID t,UUID id){return jdbc.query("SELECT * FROM refunds WHERE tenant_id=? AND id=?",this::map,t,id).stream().findFirst();} public Optional<Refund> findByTenantIdAndReference(UUID t,String ref){return jdbc.query("SELECT * FROM refunds WHERE tenant_id=? AND refund_reference=?",this::map,t,ref).stream().findFirst();} public List<Refund> findByTenantIdAndPaymentId(UUID t,UUID p){return jdbc.query("SELECT * FROM refunds WHERE tenant_id=? AND payment_id=? ORDER BY created_at",this::map,t,p);}
}

@Repository
class JdbcIdempotencyRepository implements IdempotencyRepository {
    private final JdbcTemplate jdbc; JdbcIdempotencyRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    public Optional<IdempotencyRecord> find(UUID t,String op,String key,Instant now){return jdbc.query("SELECT * FROM idempotency_keys WHERE tenant_id=? AND operation=? AND key=? AND expires_at>?",(r,n)->new IdempotencyRecord((UUID)r.getObject("id"),(UUID)r.getObject("tenant_id"),r.getString("operation"),r.getString("key"),r.getString("request_hash"),r.getObject("response_status",Integer.class),r.getString("response_body"),r.getTimestamp("created_at").toInstant(),r.getTimestamp("expires_at").toInstant()),t,op,key,Timestamp.from(now)).stream().findFirst();}
    public IdempotencyRecord save(IdempotencyRecord r){jdbc.update("INSERT INTO idempotency_keys(id,tenant_id,operation,key,request_hash,response_status,response_body,created_at,expires_at) VALUES(?,?,?,?,?,?,?,?,?)",r.id(),r.tenantId(),r.operation(),r.key(),r.requestHash(),r.responseStatus(),r.responseBody(),Timestamp.from(r.createdAt()),Timestamp.from(r.expiresAt()));return r;}
}
