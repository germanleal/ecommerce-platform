package com.company.platform.administration.api;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.company.platform.administration.application.CatalogAdministrationService;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class CatalogAdministrationControllerIntegrationTest {
    private CatalogAdministrationService service;
    private MockMvc mvc;

    @BeforeEach void setup() {
        service = mock(CatalogAdministrationService.class);
        mvc = MockMvcBuilders.standaloneSetup(new CatalogAdministrationController(service)).setControllerAdvice(new AdministrationApiExceptionHandler()).build();
    }

    @Test void listsProductsWithTenantPaginationAndSearch() throws Exception {
        UUID tenant = UUID.randomUUID();
        when(service.list(eq("products"), any(), eq(tenant), eq("note"), eq(1), eq(10)))
            .thenReturn(List.of(Map.of("id", UUID.randomUUID(), "name", "Notebook", "stock", 100)));
        mvc.perform(get("/api/admin/products").param("tenantId", tenant.toString()).param("search", "note").param("page", "1").param("size", "10"))
            .andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Notebook")).andExpect(jsonPath("$[0].stock").value(100));
        verify(service).list(eq("products"), any(), eq(tenant), eq("note"), eq(1), eq(10));
    }

    @Test void createsTaxUsingDecimalPayload() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.create(eq("taxes"), anyMap(), any())).thenReturn(Map.of("id", id, "rate", "19.0000"));
        mvc.perform(post("/api/admin/taxes").contentType(MediaType.APPLICATION_JSON).content("{\"code\":\"IVA\",\"name\":\"IVA\",\"rate\":\"19.0000\"}"))
            .andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(id.toString())).andExpect(jsonPath("$.rate").value("19.0000"));
    }

    @Test void deletesProductWithNoContent() throws Exception {
        UUID id = UUID.randomUUID();
        mvc.perform(delete("/api/admin/products/{id}", id)).andExpect(status().isNoContent());
        verify(service).deleteProduct(eq(id), any());
    }

    @Test void mapsCrossTenantDenialToForbidden() throws Exception {
        when(service.list(eq("products"), any(), any(), anyString(), anyInt(), anyInt())).thenThrow(new SecurityException("cross-tenant access denied"));
        mvc.perform(get("/api/admin/products")).andExpect(status().isForbidden()).andExpect(jsonPath("$.error").value("FORBIDDEN"));
    }
}
