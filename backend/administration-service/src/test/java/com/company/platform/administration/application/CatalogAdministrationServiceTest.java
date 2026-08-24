package com.company.platform.administration.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.company.platform.administration.infrastructure.TenantContextProvider;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class CatalogAdministrationServiceTest {
 @Test void viewerCannotWrite(){JdbcTemplate jdbc=mock(JdbcTemplate.class);TenantContextProvider context=()->UUID.randomUUID();var service=new CatalogAdministrationService(jdbc,context);var auth=new UsernamePasswordAuthenticationToken(UUID.randomUUID().toString(),"",List.of(new SimpleGrantedAuthority("TENANT_VIEWER")));assertThrows(SecurityException.class,()->service.create("categories",Map.of("name","X"),auth));verifyNoInteractions(jdbc);}
 @Test void tenantCannotRequestAnotherTenant(){JdbcTemplate jdbc=mock(JdbcTemplate.class);UUID own=UUID.randomUUID(),other=UUID.randomUUID();TenantContextProvider context=()->own;var service=new CatalogAdministrationService(jdbc,context);var auth=new UsernamePasswordAuthenticationToken(UUID.randomUUID().toString(),"",List.of(new SimpleGrantedAuthority("TENANT_ADMIN")));assertThrows(SecurityException.class,()->service.list("products",auth,other,"",0,20));verifyNoInteractions(jdbc);}
 @Test void invalidTaxRateIsRejectedBeforePersistence(){JdbcTemplate jdbc=mock(JdbcTemplate.class);UUID tenant=UUID.randomUUID();TenantContextProvider context=()->tenant;var service=new CatalogAdministrationService(jdbc,context);var auth=new UsernamePasswordAuthenticationToken(UUID.randomUUID().toString(),"",List.of(new SimpleGrantedAuthority("TENANT_ADMIN")));assertThrows(IllegalArgumentException.class,()->service.create("taxes",Map.of("code","IVA","name","Tax","rate","not-decimal"),auth));verifyNoInteractions(jdbc);}
}
