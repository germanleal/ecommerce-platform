package com.company.platform.order.api;

import com.company.platform.order.application.OrderService;
import com.company.platform.order.domain.*;
import org.springframework.http.*; import org.springframework.security.access.prepost.PreAuthorize; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal; import java.util.*;

@RestController @RequestMapping("/api/orders") public class OrderController {
 private final OrderService service; public OrderController(OrderService service){this.service=service;}
 public record LineRequest(UUID productId,UUID sellableProductId,int quantity){} public record CreateRequest(UUID customerId,UUID storeId,String currency,List<LineRequest> lines,List<LineRequest> items){} public record QuantityRequest(int quantity){}
 public record OrderResponse(UUID id,String orderNumber,UUID tenantId,UUID customerId,UUID storeId,String status,String currency,BigDecimal subtotal,BigDecimal total,List<?> lines){static OrderResponse of(Order o){return new OrderResponse(o.id(),o.orderNumber().value(),o.tenantId(),o.customerId(),o.storeId(),o.status().name(),o.currency().getCurrencyCode(),o.subtotal().amount(),o.total().amount(),o.items());}}
 private UUID currentUser(UUID requested){try{return UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());}catch(Exception e){return requested;}}
 @PostMapping @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyAuthority('ORDER_CREATE','TENANT_OWNER','TENANT_ADMIN','TENANT_MANAGER')") public OrderResponse create(@RequestBody CreateRequest r){List<LineRequest> source=r.lines()!=null?r.lines():r.items();if(source==null)source=List.of();var commands=source.stream().map(x->new OrderService.ItemCommand(x.productId()!=null?x.productId():x.sellableProductId(),null,x.quantity(),null)).toList();return OrderResponse.of(service.create(currentUser(r.customerId()),r.storeId(),Currency.getInstance(r.currency()),commands));}
 @GetMapping @PreAuthorize("hasAnyAuthority('ORDER_READ','TENANT_OWNER','TENANT_ADMIN','TENANT_MANAGER')") public List<OrderResponse> list(@RequestParam(required=false) OrderStatus status){return service.list(status).stream().map(OrderResponse::of).toList();}
 @GetMapping("/{id}") @PreAuthorize("hasAnyAuthority('ORDER_READ','TENANT_OWNER','TENANT_ADMIN','TENANT_MANAGER')") public OrderResponse get(@PathVariable UUID id){return OrderResponse.of(service.get(id));}
 @PostMapping("/{id}/lines") @PreAuthorize("hasAnyAuthority('ORDER_UPDATE','ORDER_CREATE','TENANT_OWNER','TENANT_ADMIN','TENANT_MANAGER')") public OrderResponse addLine(@PathVariable UUID id,@RequestBody LineRequest r){return OrderResponse.of(service.addLine(id,r.productId()!=null?r.productId():r.sellableProductId(),r.quantity()));}
 @PatchMapping("/{id}/lines/{lineId}") @PreAuthorize("hasAnyAuthority('ORDER_UPDATE','TENANT_OWNER','TENANT_ADMIN','TENANT_MANAGER')") public OrderResponse updateLine(@PathVariable UUID id,@PathVariable UUID lineId,@RequestBody QuantityRequest r){return OrderResponse.of(service.updateLine(id,lineId,r.quantity()));}
 @DeleteMapping("/{id}/lines/{lineId}") @ResponseStatus(HttpStatus.NO_CONTENT) @PreAuthorize("hasAnyAuthority('ORDER_UPDATE','TENANT_OWNER','TENANT_ADMIN','TENANT_MANAGER')") public void removeLine(@PathVariable UUID id,@PathVariable UUID lineId){service.removeLine(id,lineId);}
 @PostMapping("/{id}/confirm") @PreAuthorize("hasAnyAuthority('ORDER_CONFIRM','ORDER_UPDATE','TENANT_OWNER','TENANT_ADMIN','TENANT_MANAGER')") public OrderResponse confirm(@PathVariable UUID id){return OrderResponse.of(service.confirm(id));}
 @PostMapping("/{id}/cancel") @PreAuthorize("hasAnyAuthority('ORDER_CANCEL','ORDER_UPDATE','TENANT_OWNER','TENANT_ADMIN','TENANT_MANAGER')") public OrderResponse cancel(@PathVariable UUID id){return OrderResponse.of(service.cancel(id));}
}
