package com.company.platform.aigateway.spi;
public record AIUsage(int inputTokens, int outputTokens, int totalTokens) { public static AIUsage empty() { return new AIUsage(0, 0, 0); } }
