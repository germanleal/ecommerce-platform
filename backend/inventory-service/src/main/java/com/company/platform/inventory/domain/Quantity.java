package com.company.platform.inventory.domain;
import java.math.BigDecimal;
public record Quantity(BigDecimal value){public Quantity{if(value==null||value.signum()<0)throw new IllegalArgumentException("quantity must not be negative");}public static Quantity of(BigDecimal value){return new Quantity(value);}}
