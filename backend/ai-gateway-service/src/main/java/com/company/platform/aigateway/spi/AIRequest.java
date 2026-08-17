package com.company.platform.aigateway.spi;
import java.util.UUID;
public record AIRequest(String prompt, String model, UUID tenantId, UUID userId, String correlationId) { }
