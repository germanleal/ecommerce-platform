package com.company.platform.marketplace.domain.product;

public record SKU(String value) { public SKU {if(value==null||!value.matches("[A-Za-z0-9][A-Za-z0-9_-]{1,63}"))throw new IllegalArgumentException("invalid SKU");} }
