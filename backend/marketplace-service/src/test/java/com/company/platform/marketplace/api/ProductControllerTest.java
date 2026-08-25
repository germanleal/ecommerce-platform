package com.company.platform.marketplace.api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.company.platform.marketplace.application.MarketplaceApplicationService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

class ProductControllerTest {
    @Test
    void publicCatalogUsesAdministrationProductsAndExposesTaxBreakdown() {
        JdbcTemplate jdbc = Mockito.mock(JdbcTemplate.class);
        MarketplaceApplicationService service = Mockito.mock(MarketplaceApplicationService.class);
        when(jdbc.queryForList(Mockito.anyString(), any(Object[].class))).thenReturn(List.of());

        new ProductController(service, jdbc).list(null, null);

        ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
        verify(jdbc).queryForList(sql.capture(), any(Object[].class));
        assertTrue(sql.getValue().contains("administration.administration_products"));
        assertTrue(sql.getValue().contains("tax_rate"));
        assertFalse(sql.getValue().contains("FROM products p"));
    }
}
