package com.company.platform.commerce.domain.offer;
import java.util.*;
public interface CommercialRuleRepository { CommercialRule save(CommercialRule rule); List<CommercialRule> findAllByTenantId(UUID tenantId); }
