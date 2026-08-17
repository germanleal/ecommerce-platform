package com.company.platform.inventory.domain;

import java.time.Instant;
import java.util.UUID;

public final class Fulfillment {
    private final UUID id, tenantId, orderId, warehouseId, createdBy;
    private final Instant createdAt;
    private Instant startedAt, completedAt, updatedAt;
    private FulfillmentStatus status;

    private Fulfillment(UUID id, UUID tenantId, UUID orderId, UUID warehouseId,
                        FulfillmentStatus status, UUID createdBy, Instant startedAt,
                        Instant completedAt, Instant createdAt, Instant updatedAt) {
        if (id == null || tenantId == null || orderId == null || warehouseId == null
                || status == null || createdBy == null || createdAt == null || updatedAt == null) {
            throw new IllegalArgumentException("invalid fulfillment");
        }
        this.id = id; this.tenantId = tenantId; this.orderId = orderId;
        this.warehouseId = warehouseId; this.status = status; this.createdBy = createdBy;
        this.startedAt = startedAt; this.completedAt = completedAt;
        this.createdAt = createdAt; this.updatedAt = updatedAt;
    }

    public static Fulfillment create(UUID tenantId, UUID orderId, UUID warehouseId, UUID createdBy) {
        Instant now = Instant.now();
        return new Fulfillment(UUID.randomUUID(), tenantId, orderId, warehouseId,
                FulfillmentStatus.CREATED, createdBy, null, null, now, now);
    }

    public static Fulfillment rehydrate(UUID id, UUID tenantId, UUID orderId, UUID warehouseId,
                                        FulfillmentStatus status, UUID createdBy, Instant startedAt,
                                        Instant completedAt, Instant createdAt, Instant updatedAt) {
        return new Fulfillment(id, tenantId, orderId, warehouseId, status, createdBy,
                startedAt, completedAt, createdAt, updatedAt);
    }

    public void start() { transition(FulfillmentStatus.READY_FOR_PICKING, FulfillmentStatus.CREATED, FulfillmentStatus.WAITING_PAYMENT); startedAt = Instant.now(); }
    public void beginPicking() { transition(FulfillmentStatus.PICKING, FulfillmentStatus.READY_FOR_PICKING); }
    public void completePicking() { transition(FulfillmentStatus.PICKED, FulfillmentStatus.PICKING); }
    public void beginPacking() { transition(FulfillmentStatus.PACKING, FulfillmentStatus.PICKED); }
    public void completePacking() { transition(FulfillmentStatus.PACKED, FulfillmentStatus.PACKING); transition(FulfillmentStatus.READY_FOR_SHIPPING, FulfillmentStatus.PACKED); completedAt = Instant.now(); }
    public void cancel() { transition(FulfillmentStatus.CANCELLED, FulfillmentStatus.CREATED, FulfillmentStatus.WAITING_PAYMENT, FulfillmentStatus.READY_FOR_PICKING, FulfillmentStatus.PICKING, FulfillmentStatus.PICKED, FulfillmentStatus.PACKING); }

    private void transition(FulfillmentStatus to, FulfillmentStatus... from) {
        for (FulfillmentStatus allowed : from) if (status == allowed) { status = to; updatedAt = Instant.now(); return; }
        throw new IllegalStateException("invalid fulfillment transition: " + status + " -> " + to);
    }

    public UUID id() { return id; } public UUID tenantId() { return tenantId; } public UUID orderId() { return orderId; }
    public UUID warehouseId() { return warehouseId; } public UUID createdBy() { return createdBy; }
    public FulfillmentStatus status() { return status; } public Instant startedAt() { return startedAt; }
    public Instant completedAt() { return completedAt; } public Instant createdAt() { return createdAt; }
    public Instant updatedAt() { return updatedAt; }
}
