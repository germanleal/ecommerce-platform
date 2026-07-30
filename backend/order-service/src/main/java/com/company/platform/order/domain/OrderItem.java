package com.company.platform.order.domain;
import java.util.*;
public record OrderItem(UUID id,UUID orderId,UUID sellableProductId,String productSnapshot,int quantity,Money unitPrice,Money subtotal){public OrderItem{if(orderId==null||sellableProductId==null||productSnapshot==null||productSnapshot.isBlank()||quantity<=0||unitPrice==null)throw new IllegalArgumentException("invalid order item");if(id==null)id=UUID.randomUUID();if(subtotal==null)subtotal=unitPrice.multiply(quantity);if(!subtotal.equals(unitPrice.multiply(quantity)))throw new IllegalArgumentException("invalid subtotal");}}
