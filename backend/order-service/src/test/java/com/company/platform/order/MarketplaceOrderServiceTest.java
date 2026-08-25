package com.company.platform.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.company.platform.order.application.CommerceProductPort;
import com.company.platform.order.application.OrderService;
import com.company.platform.order.domain.Order;
import com.company.platform.order.domain.OrderRepository;
import com.company.platform.order.domain.OrderStatus;
import com.company.platform.order.infrastructure.KafkaOrderEventPublisher;
import com.company.platform.order.infrastructure.TenantContextProvider;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class MarketplaceOrderServiceTest {
    @Test
    void createsAnOrderForTheProductsSellerTenantInsteadOfTheCustomersTenant() {
        UUID customerTenant = UUID.randomUUID(), sellerTenant = UUID.randomUUID();
        UUID customer = UUID.randomUUID(), store = UUID.randomUUID(), product = UUID.randomUUID();
        OrderRepository repository = mock(OrderRepository.class);
        when(repository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CommerceProductPort catalog = mock(CommerceProductPort.class);
        when(catalog.resolve(sellerTenant, product)).thenReturn(new CommerceProductPort.ProductSnapshot(product, sellerTenant, store, "SKU-1", "Product", new BigDecimal("12.50"), "USD"));
        TenantContextProvider tenant = () -> customerTenant;
        OrderService service = new OrderService(repository, tenant, mock(KafkaOrderEventPublisher.class), catalog);

        UUID purchaseId = UUID.randomUUID();
        Order order = service.createForTenant(purchaseId, sellerTenant, customer, store, Currency.getInstance("USD"), List.of(new OrderService.ItemCommand(product, null, 2, null)));

        assertEquals(purchaseId, order.purchaseId());
        assertEquals(sellerTenant, order.tenantId());
        assertEquals(store, order.storeId());
        assertEquals(new BigDecimal("25.00"), order.total().amount());
        verify(catalog).resolve(sellerTenant, product);
    }

    @Test
    void managementCompletesOnlyTheRequestedSellersOrder() {
        UUID sellerTenant = UUID.randomUUID(), customer = UUID.randomUUID(), store = UUID.randomUUID(), product = UUID.randomUUID();
        OrderRepository repository = mock(OrderRepository.class);
        when(repository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CommerceProductPort catalog = mock(CommerceProductPort.class);
        when(catalog.resolve(sellerTenant, product)).thenReturn(new CommerceProductPort.ProductSnapshot(product, sellerTenant, store, "SKU-2", "Product", new BigDecimal("8.00"), "USD"));
        OrderService service = new OrderService(repository, () -> UUID.randomUUID(), mock(KafkaOrderEventPublisher.class), catalog);
        Order order = service.createForTenant(UUID.randomUUID(), sellerTenant, customer, store, Currency.getInstance("USD"), List.of(new OrderService.ItemCommand(product, null, 1, null)));
        when(repository.findByTenantIdAndId(sellerTenant, order.id())).thenReturn(java.util.Optional.of(order));

        service.confirmForTenant(sellerTenant, order.id());
        service.startProcessingForTenant(sellerTenant, order.id());
        service.completeForTenant(sellerTenant, order.id());

        assertEquals(OrderStatus.COMPLETED, order.status());
    }
}
