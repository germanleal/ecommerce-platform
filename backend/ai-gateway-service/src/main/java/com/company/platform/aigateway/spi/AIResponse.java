package com.company.platform.aigateway.spi;
public record AIResponse(String content, String provider, String model, AIUsage usage, String requestId, String correlationId, long latencyMs, String status) { }
