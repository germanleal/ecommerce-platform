package com.company.platform.inventory.api;

import com.company.platform.inventory.application.FulfillmentService;
import com.company.platform.inventory.domain.Fulfillment;
import java.math.BigDecimal;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fulfillment")
public class FulfillmentController {
    private final FulfillmentService service;
    public FulfillmentController(FulfillmentService service) { this.service = service; }
    public record CreateRequest(UUID orderId, UUID warehouseId, UUID createdBy) {}
    public record PickingRequest(UUID inventoryId, BigDecimal quantity, String warehouseLocation, UUID operatorId) {}
    public record PackingRequest(String packageNumber, BigDecimal packageWeight, BigDecimal packageVolume) {}
    @PostMapping public Fulfillment create(@RequestBody CreateRequest r) { return service.create(r.orderId(), r.warehouseId(), r.createdBy()); }
    @GetMapping public List<Fulfillment> byOrder(@RequestParam UUID orderId) { return service.listByOrder(orderId); }
    @GetMapping("/order/{orderId}") public List<Fulfillment> byOrderPath(@PathVariable UUID orderId) { return service.listByOrder(orderId); }
    @GetMapping("/{id}") public Fulfillment get(@PathVariable UUID id) { return service.get(id); }
    @PostMapping("/{id}/start") public Fulfillment start(@PathVariable UUID id) { return service.start(id); }
    @PostMapping("/{id}/picking") public Fulfillment picking(@PathVariable UUID id, @RequestBody PickingRequest r) { return service.executePicking(id, r.inventoryId(), r.quantity(), r.warehouseLocation(), r.operatorId()); }
    @PostMapping("/{id}/picking/complete") public Fulfillment completePicking(@PathVariable UUID id) { return service.completePicking(id); }
    @PostMapping("/{id}/packing") public Fulfillment packing(@PathVariable UUID id, @RequestBody PackingRequest r) { return service.executePacking(id, r.packageNumber(), r.packageWeight(), r.packageVolume()); }
    @PostMapping("/{id}/packing/complete") public Fulfillment completePacking(@PathVariable UUID id) { return service.completePacking(id); }
    @PostMapping("/{id}/cancel") public Fulfillment cancel(@PathVariable UUID id) { return service.cancel(id); }
}
