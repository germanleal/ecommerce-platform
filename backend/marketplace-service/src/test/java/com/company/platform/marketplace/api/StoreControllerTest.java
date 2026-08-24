package com.company.platform.marketplace.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

class StoreControllerTest {
    private final JdbcTemplate jdbc = Mockito.mock(JdbcTemplate.class);
    private final StoreController controller = new StoreController(jdbc);

    @Test
    void listsOnlyActiveAdministrationCompanies() {
        UUID companyId = UUID.randomUUID();
        when(jdbc.queryForList(anyString(), any(Object[].class)))
            .thenReturn(List.of(Map.of("id", companyId, "name", "Demo Company")));

        List<Map<String, Object>> stores = controller.list(null);

        assertEquals(List.of(Map.of("id", companyId, "name", "Demo Company")), stores);
        ArgumentCaptor<String> sql = ArgumentCaptor.forClass(String.class);
        verify(jdbc).queryForList(sql.capture(), any(Object[].class));
        assertFalse(sql.getValue().contains("public.stores"));
        assertTrue(sql.getValue().contains("administration.administration_companies"));
    }

    @Test
    void appliesSearchToCompanyNameAndGeneratedSlug() {
        when(jdbc.queryForList(anyString(), any(Object[].class))).thenReturn(List.of());

        controller.list(" Demo ");

        verify(jdbc).queryForList(anyString(), aryEq(new Object[]{"%Demo%", "%Demo%"}));
    }
}
