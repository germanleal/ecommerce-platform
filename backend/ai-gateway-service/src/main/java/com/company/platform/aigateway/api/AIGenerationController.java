package com.company.platform.aigateway.api;
import com.company.platform.aigateway.application.AIGenerationService;
import com.company.platform.aigateway.spi.AIResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/ai") public class AIGenerationController { private final AIGenerationService service; public AIGenerationController(AIGenerationService service){this.service=service;} public record GenerateRequest(@NotBlank String prompt,String model){} @PostMapping("/generate") @PreAuthorize("hasAnyAuthority('AI_USER','AI_ADMIN','ROLE_AI_USER','ROLE_AI_ADMIN','TENANT_ADMIN','TENANT_MANAGER','TENANT_OWNER','ROLE_TENANT_ADMIN')") public AIResponse generate(@Valid @RequestBody GenerateRequest request){return service.generate(request.prompt(),request.model());} }
