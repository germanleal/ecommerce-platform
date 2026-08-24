package com.company.platform.order.application;

import com.company.platform.order.domain.*;
import com.company.platform.order.domain.events.OrderEvent;
import com.company.platform.order.infrastructure.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.*;

@Service public class OrderService {
 private final OrderRepository orders; private final TenantContextProvider tenant; private final KafkaOrderEventPublisher events; private final CommerceProductPort commerce;
 public OrderService(OrderRepository orders,TenantContextProvider tenant,KafkaOrderEventPublisher events,CommerceProductPort commerce){this.orders=orders;this.tenant=tenant;this.events=events;this.commerce=commerce;}
 private UUID t(){return tenant.currentTenantId();}
 @Transactional public Order create(UUID customerId,UUID storeId,Currency currency,List<ItemCommand> commands){return createForTenant(t(),customerId,storeId,currency,commands);}
 @Transactional public Order createForTenant(UUID tenantId,UUID customerId,UUID storeId,Currency currency,List<ItemCommand> commands){if(tenantId==null||customerId==null||storeId==null||currency==null||commands==null||commands.isEmpty())throw new IllegalArgumentException("INVALID_ORDER");Order order=Order.create(tenantId,new OrderNumber("ORD-"+Instant.now().toEpochMilli()+"-"+UUID.randomUUID().toString().substring(0,8)),customerId,storeId,currency);for(var command:commands){var snap=commerce.resolve(tenantId,command.productId());if(!tenantId.equals(snap.tenantId())||!storeId.equals(snap.storeId()))throw new IllegalArgumentException("PRODUCT_STORE_MISMATCH");if(!currency.getCurrencyCode().equals(snap.currency()))throw new IllegalArgumentException("INVALID_MONEY");order.addItem(new OrderItem(null,order.id(),snap.productId(),snap.name()+"|sku="+snap.sku(),command.quantity(),new Money(snap.amount(),currency),null));}order.submit();var saved=orders.save(order);emit("ORDER_CREATED",saved);return saved;}
 @Transactional public Order addLine(UUID id,UUID productId,int quantity){Order o=get(id);var snap=commerce.resolve(t(),productId);if(!o.tenantId().equals(snap.tenantId())||!o.storeId().equals(snap.storeId()))throw new IllegalArgumentException("PRODUCT_STORE_MISMATCH");if(!o.currency().getCurrencyCode().equals(snap.currency()))throw new IllegalArgumentException("INVALID_MONEY");o.addItem(new OrderItem(null,o.id(),snap.productId(),snap.name()+"|sku="+snap.sku(),quantity,new Money(snap.amount(),o.currency()),null));return orders.save(o);}
 @Transactional public Order updateLine(UUID id,UUID lineId,int quantity){Order o=get(id);o.updateItem(lineId,quantity);return orders.save(o);}
 @Transactional public void removeLine(UUID id,UUID lineId){Order o=get(id);o.removeItem(lineId);orders.save(o);}
 @Transactional public Order confirm(UUID id){Order o=get(id);o.confirm();var saved=orders.save(o);emit("ORDER_CONFIRMED",saved);return saved;}
 @Transactional public Order cancel(UUID id){Order o=get(id);o.cancel();var saved=orders.save(o);emit("ORDER_CANCELLED",saved);return saved;}
 public Order get(UUID id){return orders.findByTenantIdAndId(t(),id).orElseThrow(()->new NoSuchElementException("ORDER_NOT_FOUND"));}
 public List<Order> list(OrderStatus status){return orders.findByTenantId(t(),status);}
 public List<Order> byCustomer(UUID id){return orders.findByTenantIdAndCustomerId(t(),id);} public List<Order> byStore(UUID id){return orders.findByTenantIdAndStoreId(t(),id);}
 private void emit(String type,Order o){events.publish(new OrderEvent(UUID.randomUUID(),type,o.id(),o.tenantId(),Instant.now(),Map.of("orderId",o.id().toString(),"tenantId",o.tenantId().toString(),"customerId",o.customerId().toString(),"total",o.total().amount(),"currency",o.currency().getCurrencyCode(),"lines",o.items())));}
 public record ItemCommand(UUID productId,String productSnapshot,int quantity,java.math.BigDecimal amount){}
}
