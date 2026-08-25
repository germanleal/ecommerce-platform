package com.company.platform.analytics.application;

import com.company.platform.analytics.infrastructure.TenantContextProvider;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsQueryService {
    private final JdbcTemplate jdbc;
    private final TenantContextProvider tenant;

    public AnalyticsQueryService(JdbcTemplate jdbc, TenantContextProvider tenant) { this.jdbc = jdbc; this.tenant = tenant; }
    private UUID currentTenant() { return tenant.currentTenantId(); }

    private Range range(String from, String to) {
        try {
            LocalDate start = from == null ? LocalDate.of(1970, 1, 1) : LocalDate.parse(from);
            LocalDate end = to == null ? LocalDate.now(ZoneOffset.UTC).plusDays(1) : LocalDate.parse(to).plusDays(1);
            if (start.isAfter(end.minusDays(1))) throw new IllegalArgumentException("INVALID_DATE_RANGE");
            return new Range(start.atStartOfDay(ZoneOffset.UTC).toInstant(), end.atStartOfDay(ZoneOffset.UTC).toInstant());
        } catch (DateTimeException exception) { throw new IllegalArgumentException("INVALID_DATE_RANGE", exception); }
    }

    private record Range(Instant from, Instant to) { }
    private Timestamp from(Range range) { return Timestamp.from(range.from()); }
    private Timestamp to(Range range) { return Timestamp.from(range.to()); }

    public Map<String, Object> overview(String start, String end) {
        Range range = range(start, end);
        String sql = "WITH scoped_orders AS (SELECT status, subtotal FROM public.orders WHERE tenant_id=? AND created_at>=? AND created_at<?), scoped_metrics AS (SELECT event_type, amount, quantity FROM analytics_metrics WHERE tenant_id=? AND occurred_at>=? AND occurred_at<?) " +
            "SELECT (SELECT COUNT(*) FROM scoped_orders) orders_created, " +
            "(SELECT COUNT(*) FROM scoped_orders WHERE status IN ('CONFIRMED','PROCESSING','COMPLETED')) orders_confirmed, " +
            "(SELECT COUNT(*) FROM scoped_orders WHERE status='CANCELLED') orders_cancelled, " +
            "COALESCE((SELECT SUM(subtotal) FROM scoped_orders WHERE status IN ('CONFIRMED','PROCESSING','COMPLETED')),0) total_sales, " +
            "COALESCE((SELECT SUM(amount) FROM scoped_metrics WHERE event_type='PAYMENT_PAID'),0) paid_amount, " +
            "(SELECT COUNT(*) FROM scoped_metrics WHERE event_type='PAYMENT_FAILED') failed_payments, " +
            "COALESCE((SELECT SUM(quantity) FROM scoped_metrics WHERE event_type LIKE 'INVENTORY_%'),0) inventory_quantity";
        return jdbc.queryForMap(sql, currentTenant(), from(range), to(range), currentTenant(), from(range), to(range));
    }

    public List<Map<String, Object>> sales(String start, String end) {
        Range range = range(start, end);
        return jdbc.queryForList("SELECT DATE(occurred_at AT TIME ZONE 'UTC') day,COUNT(*) FILTER(WHERE event_type='ORDER_CONFIRMED') orders,COALESCE(SUM(amount) FILTER(WHERE event_type='ORDER_CONFIRMED'),0) gross_sales,COALESCE(SUM(amount) FILTER(WHERE event_type='PAYMENT_PAID'),0) paid_sales FROM analytics_metrics WHERE tenant_id=? AND occurred_at>=? AND occurred_at<? GROUP BY 1 ORDER BY 1 DESC", currentTenant(), from(range), to(range));
    }
    public List<Map<String, Object>> orders(String start, String end) {
        Range range = range(start, end);
        return jdbc.queryForList("SELECT event_type,COUNT(*) count,COALESCE(SUM(amount),0) amount FROM analytics_metrics WHERE tenant_id=? AND occurred_at>=? AND occurred_at<? AND event_type LIKE 'ORDER_%' GROUP BY event_type ORDER BY event_type", currentTenant(), from(range), to(range));
    }
    public List<Map<String, Object>> payments(String start, String end) {
        Range range = range(start, end);
        return jdbc.queryForList("SELECT event_type,COUNT(*) count,COALESCE(SUM(amount),0) amount FROM analytics_metrics WHERE tenant_id=? AND occurred_at>=? AND occurred_at<? AND event_type LIKE 'PAYMENT_%' GROUP BY event_type ORDER BY event_type", currentTenant(), from(range), to(range));
    }
    public List<Map<String, Object>> inventory(String start, String end) {
        Range range = range(start, end);
        return jdbc.queryForList("SELECT event_type,COUNT(*) count,COALESCE(SUM(quantity),0) quantity FROM analytics_metrics WHERE tenant_id=? AND occurred_at>=? AND occurred_at<? AND event_type LIKE 'INVENTORY_%' GROUP BY event_type ORDER BY event_type", currentTenant(), from(range), to(range));
    }
    public List<Map<String, Object>> products(String start, String end) {
        Range range = range(start, end);
        return jdbc.queryForList("SELECT product_id,COUNT(*) events,COALESCE(SUM(quantity),0) quantity,COALESCE(SUM(amount),0) amount FROM analytics_metrics WHERE tenant_id=? AND product_id IS NOT NULL AND occurred_at>=? AND occurred_at<? GROUP BY product_id ORDER BY quantity DESC LIMIT 100", currentTenant(), from(range), to(range));
    }
    public List<Map<String, Object>> dashboard() { return sales(null, null); }
}
