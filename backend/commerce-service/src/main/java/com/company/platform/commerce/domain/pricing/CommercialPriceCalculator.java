package com.company.platform.commerce.domain.pricing;
import java.math.BigDecimal;
public final class CommercialPriceCalculator { private CommercialPriceCalculator(){} public static Money applyPercentageDiscount(Money base,BigDecimal percentage){if(base==null)throw new IllegalArgumentException("base price required");return base.percentageDiscount(percentage);} }
