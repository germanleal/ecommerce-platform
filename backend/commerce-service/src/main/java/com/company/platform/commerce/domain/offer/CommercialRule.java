package com.company.platform.commerce.domain.offer;
import java.util.UUID;
public record CommercialRule(UUID id,UUID tenantId,String name,String description,CommercialRuleType type,String status) { public CommercialRule {if(tenantId==null||name==null||name.isBlank()||type==null)throw new IllegalArgumentException("invalid commercial rule");if(id==null)id=UUID.randomUUID();if(status==null)status="ACTIVE";} }
