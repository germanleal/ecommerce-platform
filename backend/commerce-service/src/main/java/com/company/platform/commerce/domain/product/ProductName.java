package com.company.platform.commerce.domain.product;
public record ProductName(String value) { public ProductName { if(value==null||value.isBlank()||value.length()>255) throw new IllegalArgumentException("invalid product name"); } }
