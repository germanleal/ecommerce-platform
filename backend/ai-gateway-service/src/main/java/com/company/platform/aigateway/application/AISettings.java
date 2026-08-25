package com.company.platform.aigateway.application;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AISettings {
    private final boolean enabled; private final String provider; private final String model; private final Duration timeout; private final int maxTokens; private final int rateLimit;
    public AISettings(@Value("${AI_ENABLED:false}") boolean enabled, @Value("${AI_PROVIDER:reference}") String provider, @Value("${AI_MODEL:reference-model}") String model, @Value("${AI_TIMEOUT:5s}") Duration timeout, @Value("${AI_MAX_TOKENS:1024}") int maxTokens, @Value("${AI_RATE_LIMIT:30}") int rateLimit) { this.enabled=enabled; this.provider=provider; this.model=model; this.timeout=timeout; this.maxTokens=maxTokens; this.rateLimit=rateLimit; }
    public boolean enabled(){return enabled;} public String provider(){return provider;} public String model(){return model;} public Duration timeout(){return timeout;} public int maxTokens(){return maxTokens;} public int rateLimit(){return rateLimit;}
}
