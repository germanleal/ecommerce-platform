package com.company.platform.inventory.infrastructure;

import com.company.platform.inventory.application.FulfillmentStore;
import com.company.platform.inventory.domain.*;
import java.sql.Timestamp;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcFulfillmentStore implements FulfillmentStore {
    private final JdbcTemplate jdbc;
    public JdbcFulfillmentStore(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    public Fulfillment save(Fulfillment f) {
        jdbc.update("INSERT INTO fulfillments(id,tenant_id,order_id,warehouse_id,status,started_at,completed_at,created_by,created_at,updated_at) VALUES(?,?,?,?,?,?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET status=?,started_at=?,completed_at=?,updated_at=?",
                f.id(), f.tenantId(), f.orderId(), f.warehouseId(), f.status().name(), ts(f.startedAt()), ts(f.completedAt()), f.createdBy(), ts(f.createdAt()), ts(f.updatedAt()), f.status().name(), ts(f.startedAt()), ts(f.completedAt()), ts(f.updatedAt()));
        return f;
    }
    private Timestamp ts(java.time.Instant value) { return value == null ? null : Timestamp.from(value); }
    private Fulfillment map(java.sql.ResultSet r, int n) throws java.sql.SQLException {
        return Fulfillment.rehydrate((UUID) r.getObject("id"), (UUID) r.getObject("tenant_id"),
                (UUID) r.getObject("order_id"), (UUID) r.getObject("warehouse_id"),
                FulfillmentStatus.valueOf(r.getString("status")), (UUID) r.getObject("created_by"),
                instant(r, "started_at"), instant(r, "completed_at"), instant(r, "created_at"), instant(r, "updated_at"));
    }
    private java.time.Instant instant(java.sql.ResultSet r, String column) throws java.sql.SQLException { Timestamp value = r.getTimestamp(column); return value == null ? null : value.toInstant(); }
    public Optional<Fulfillment> find(UUID tenantId, UUID id) { return jdbc.query("SELECT * FROM fulfillments WHERE tenant_id=? AND id=?", this::map, tenantId, id).stream().findFirst(); }
    public List<Fulfillment> findByOrder(UUID tenantId, UUID orderId) { return jdbc.query("SELECT * FROM fulfillments WHERE tenant_id=? AND order_id=? ORDER BY created_at DESC", this::map, tenantId, orderId); }
    public PickingTask save(PickingTask p) { jdbc.update("INSERT INTO picking_tasks(id,tenant_id,fulfillment_id,inventory_id,quantity,warehouse_location,operator_id,status,picked_at) VALUES(?,?,?,?,?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET status=?,picked_at=?", p.id(),p.tenantId(),p.fulfillmentId(),p.inventoryId(),p.quantity(),p.warehouseLocation(),p.operatorId(),p.status().name(),p.pickedAt(),p.status().name(),p.pickedAt()); return p; }
    public PackingTask save(PackingTask p) { jdbc.update("INSERT INTO packing_tasks(id,tenant_id,fulfillment_id,package_number,package_weight,package_volume,status,packed_at) VALUES(?,?,?,?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET status=?,packed_at=?", p.id(),p.tenantId(),p.fulfillmentId(),p.packageNumber(),p.packageWeight(),p.packageVolume(),p.status().name(),p.packedAt(),p.status().name(),p.packedAt()); return p; }
}
