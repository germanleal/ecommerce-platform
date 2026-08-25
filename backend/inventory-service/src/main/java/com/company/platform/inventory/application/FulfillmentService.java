package com.company.platform.inventory.application;

import com.company.platform.inventory.domain.*;
import com.company.platform.inventory.infrastructure.InventoryEventPublisher;
import com.company.platform.inventory.infrastructure.TenantContextProvider;
import java.math.BigDecimal;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FulfillmentService {
    private final FulfillmentStore store;
    private final TenantContextProvider tenant;
    private final InventoryEventPublisher events;
    private final InventoryService inventory;

    public FulfillmentService(FulfillmentStore store, TenantContextProvider tenant, InventoryEventPublisher events, InventoryService inventory) {
        this.store = store; this.tenant = tenant; this.events = events; this.inventory = inventory;
    }
    @Transactional public Fulfillment create(UUID orderId, UUID warehouseId, UUID createdBy) {
        Fulfillment f = store.save(Fulfillment.create(tenant.currentTenantId(), orderId, warehouseId, createdBy));
        events.publish("FulfillmentCreatedEvent", f.tenantId(), f.id(), f); return f;
    }
    @Transactional public Fulfillment createFromEvent(UUID tenantId, UUID orderId, UUID warehouseId, UUID createdBy) {
        Fulfillment f = store.save(Fulfillment.create(tenantId, orderId, warehouseId, createdBy));
        events.publish("FulfillmentCreatedEvent", tenantId, f.id(), f); return f;
    }
    public Fulfillment get(UUID id) { return store.find(tenant.currentTenantId(), id).orElseThrow(() -> new NoSuchElementException("fulfillment not found")); }
    public List<Fulfillment> listByOrder(UUID orderId) { return store.findByOrder(tenant.currentTenantId(), orderId); }
    @Transactional public Fulfillment start(UUID id) { Fulfillment f=get(id); f.start(); Fulfillment saved=store.save(f); events.publish("FulfillmentStartedEvent",f.tenantId(),f.id(),f); return saved; }
    @Transactional public Fulfillment executePicking(UUID id,UUID inventoryId,BigDecimal quantity,String location,UUID operatorId) { Fulfillment f=get(id); f.beginPicking(); inventory.consumeReserved(inventoryId,quantity); store.save(new PickingTask(UUID.randomUUID(),f.tenantId(),f.id(),inventoryId,quantity,location,operatorId,PickingStatus.IN_PROGRESS,null)); Fulfillment saved=store.save(f); events.publish("PickingStartedEvent",f.tenantId(),f.id(),f); return saved; }
    @Transactional public Fulfillment completePicking(UUID id) { Fulfillment f=get(id); f.completePicking(); Fulfillment saved=store.save(f); events.publish("PickingCompletedEvent",f.tenantId(),f.id(),f); return saved; }
    @Transactional public Fulfillment executePacking(UUID id,String packageNumber,BigDecimal weight,BigDecimal volume) { Fulfillment f=get(id); f.beginPacking(); store.save(new PackingTask(UUID.randomUUID(),f.tenantId(),f.id(),packageNumber,weight,volume,PackingStatus.IN_PROGRESS,null)); Fulfillment saved=store.save(f); events.publish("PackingStartedEvent",f.tenantId(),f.id(),f); return saved; }
    @Transactional public Fulfillment completePacking(UUID id) { Fulfillment f=get(id); f.completePacking(); Fulfillment saved=store.save(f); events.publish("PackingCompletedEvent",f.tenantId(),f.id(),f); events.publish("FulfillmentCompletedEvent",f.tenantId(),f.id(),f); events.publish("ReadyForShippingEvent",f.tenantId(),f.id(),f); return saved; }
    @Transactional public Fulfillment cancel(UUID id) { Fulfillment f=get(id); f.cancel(); Fulfillment saved=store.save(f); events.publish("FulfillmentCancelledEvent",f.tenantId(),f.id(),f); return saved; }
}
