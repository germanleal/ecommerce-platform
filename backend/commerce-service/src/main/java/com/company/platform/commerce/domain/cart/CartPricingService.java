package com.company.platform.commerce.domain.cart;
import java.math.BigDecimal;
public final class CartPricingService { public CartSummary summarize(Cart cart){BigDecimal subtotal=cart.items().stream().map(i->i.unitPrice().amount().multiply(BigDecimal.valueOf(i.quantity()))).reduce(BigDecimal.ZERO,BigDecimal::add);int quantity=cart.items().stream().mapToInt(CartItem::quantity).sum();return new CartSummary(subtotal,quantity);} public record CartSummary(BigDecimal subtotal,int totalQuantity){} }
