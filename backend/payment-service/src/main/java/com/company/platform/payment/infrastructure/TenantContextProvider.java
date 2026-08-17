package com.company.platform.payment.infrastructure;

import java.util.UUID;

public interface TenantContextProvider { UUID currentTenantId(); }
