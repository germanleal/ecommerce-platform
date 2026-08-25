package com.company.platform.aigateway.application;

import com.company.platform.aigateway.infrastructure.TenantContextProvider;
import com.company.platform.aigateway.spi.*;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.*;
import org.slf4j.MDC;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class AIGenerationService {
    private final ProviderRegistry providers; private final TenantContextProvider tenant; private final AISettings settings; private final AIRateLimiter limiter; private final JdbcTemplate jdbc; private final MeterRegistry metrics;
    public AIGenerationService(ProviderRegistry providers,TenantContextProvider tenant,AISettings settings,AIRateLimiter limiter,JdbcTemplate jdbc,MeterRegistry metrics){this.providers=providers;this.tenant=tenant;this.settings=settings;this.limiter=limiter;this.jdbc=jdbc;this.metrics=metrics;}
    public AIResponse generate(String prompt,String requestedModel){
        if(prompt==null||prompt.isBlank())throw new AIException("AI_INVALID_REQUEST",400,"prompt is required");
        if(prompt.length()>8000)throw new AIException("AI_INVALID_REQUEST",400,"prompt exceeds the maximum length");
        String model=requestedModel==null||requestedModel.isBlank()?settings.model():requestedModel;
        if(!settings.model().equals(model))throw new AIException("AI_INVALID_REQUEST",400,"model is not allowed");
        UUID tenantId; try{tenantId=tenant.currentTenantId();}catch(RuntimeException e){throw new AIException("AI_UNAUTHORIZED",401,"authenticated tenant context required");}
        UUID userId=userId(); String key=tenantId+":"+(userId==null?"unknown":userId)+":generate";
        if(!limiter.allow(key,settings.rateLimit()))throw new AIException("AI_RATE_LIMITED",429,"AI rate limit exceeded");
        if(!settings.enabled())throw new AIException("AI_CONFIGURATION_ERROR",503,"AI provider is disabled");
        AIProvider provider=providers.find(settings.provider()).orElseThrow(()->new AIException("AI_CONFIGURATION_ERROR",503,"configured AI provider is unavailable"));
        String correlation=MDC.get("correlationId"); if(correlation==null)correlation=UUID.randomUUID().toString(); String requestId=UUID.randomUUID().toString(); Instant started=Instant.now();
        metrics.counter("ai_requests_total","provider",provider.id(),"model",model).increment();
        try{AIRequest request=new AIRequest(prompt,model,tenantId,userId,correlation); AIResponse response=CompletableFuture.supplyAsync(()->provider.generate(request)).orTimeout(settings.timeout().toMillis(),TimeUnit.MILLISECONDS).join(); long latency=java.time.Duration.between(started,Instant.now()).toMillis(); metrics.timer("ai_request_latency","provider",provider.id(),"model",model).record(latency,TimeUnit.MILLISECONDS); AIResponse normalized=new AIResponse(response.content(),provider.id(),model,response.usage(),requestId,correlation,latency,"COMPLETED"); audit(tenantId,userId,provider.id(),model,"AI_GENERATE_COMPLETED","COMPLETED",correlation,latency); return normalized;}catch(CompletionException e){Throwable cause=e.getCause();long latency=java.time.Duration.between(started,Instant.now()).toMillis();metrics.counter("ai_requests_failed","provider",provider.id(),"model",model).increment();if(cause instanceof TimeoutException){audit(tenantId,userId,provider.id(),model,"AI_GENERATE_FAILED","TIMEOUT",correlation,latency);throw new AIException("AI_TIMEOUT",504,"AI provider timed out");}audit(tenantId,userId,provider.id(),model,"AI_GENERATE_FAILED","FAILED",correlation,latency);throw new AIException("AI_PROVIDER_UNAVAILABLE",503,"AI provider is unavailable");}
    }
    private UUID userId(){Authentication a=SecurityContextHolder.getContext().getAuthentication();if(a instanceof JwtAuthenticationToken j){try{return UUID.fromString(j.getToken().getSubject());}catch(Exception ignored){}}return null;}
    private void audit(UUID tenantId,UUID userId,String provider,String model,String action,String status,String correlation,long latency){try{jdbc.update("INSERT INTO ai_audit(id,tenant_id,user_id,provider,model,action,status,correlation_id,latency_ms,created_at) VALUES(?,?,?,?,?,?,?,?,?,?)",UUID.randomUUID(),tenantId,userId,provider,model,action,status,UUID.fromString(correlation),latency,java.sql.Timestamp.from(Instant.now()));}catch(Exception ignored){ /* audit failure never exposes prompt data */ }}
}
