package com.company.platform.inventory.domain;
public record WarehouseCode(String value){public WarehouseCode{if(value==null||value.isBlank()||value.length()>64)throw new IllegalArgumentException("invalid warehouse code");}}
