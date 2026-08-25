package com.company.platform.order.domain;
public record OrderNumber(String value){public OrderNumber{if(value==null||value.isBlank())throw new IllegalArgumentException("order number required");}}
