package com.company.platform.tenant.application.services;

import com.company.platform.tenant.domain.store.*; import com.company.platform.tenant.domain.tenant.Slug; import org.springframework.stereotype.Service; import java.util.List; import java.util.UUID;

@Service
public class StoreApplicationService {
    private final StoreRepository repository; public StoreApplicationService(StoreRepository repository){this.repository=repository;}
    public Store create(UUID tenantId,String name,String slug){return repository.save(Store.create(tenantId,name,new Slug(slug),java.util.Map.of()));}
    public List<Store> list(UUID tenantId){return repository.findByTenantId(tenantId);}
}
