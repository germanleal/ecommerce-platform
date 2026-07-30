package com.company.platform.marketplace.domain.category;

import java.util.UUID;

public record Category(UUID id,UUID tenantId,UUID catalogId,String name,String description,String status) {
    public Category {if(tenantId==null||catalogId==null)throw new IllegalArgumentException("tenant and catalog required");if(name==null||name.isBlank())throw new IllegalArgumentException("category name required");if(id==null)id=UUID.randomUUID();if(status==null)status="ACTIVE";}
}
