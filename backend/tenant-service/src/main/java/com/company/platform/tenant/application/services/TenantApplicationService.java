package com.company.platform.tenant.application.services;

import com.company.platform.tenant.domain.tenant.*; import org.springframework.stereotype.Service; import java.util.Map;

@Service
public class TenantApplicationService {
    private final TenantRepository repository; public TenantApplicationService(TenantRepository repository){this.repository=repository;}
    public Tenant create(String name,String slug){return repository.save(Tenant.create(new TenantName(name),new Slug(slug),Map.of()));}
    public Tenant get(TenantId id){return repository.findById(id).orElseThrow(()->new TenantNotFoundException(id.value().toString()));}
    public Tenant activate(TenantId id){var t=get(id);t.activate();return repository.save(t);}
}
