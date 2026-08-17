package com.company.platform.integration.api;

import com.company.platform.integration.application.EnterpriseIntegrationService;
import com.company.platform.integration.domain.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/integrations", "/integration/integrations"})
public class IntegrationController {
    private final EnterpriseIntegrationService service;
    public IntegrationController(EnterpriseIntegrationService service) { this.service = service; }
    public record IntegrationRequest(String type, String provider, String endpoint, String authenticationType) {}
    public record StatusRequest(String status) {}

    @PostMapping public IntegrationConfiguration create(@RequestBody IntegrationRequest request) { return service.create(request.type(), request.provider(), request.endpoint(), request.authenticationType()); }
    @GetMapping public List<IntegrationConfiguration> list() { return service.list(); }
    @GetMapping("/{id}") public IntegrationConfiguration get(@PathVariable UUID id) { return service.get(id); }
    @PutMapping("/{id}") public IntegrationConfiguration update(@PathVariable UUID id, @RequestBody IntegrationRequest request) { return service.update(id, request.type(), request.provider(), request.endpoint(), request.authenticationType()); }
    @PatchMapping("/{id}/status") public IntegrationConfiguration status(@PathVariable UUID id, @RequestBody StatusRequest request) { return service.changeStatus(id, request.status()); }
    @PostMapping("/{id}/test") public IntegrationExecution test(@PathVariable UUID id, @RequestHeader(value = "Idempotency-Key", required = false) String key) { return service.test(id, key); }
    @GetMapping("/{id}/executions") public List<IntegrationExecution> executions(@PathVariable UUID id) { return service.executions(id); }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> notFound(NoSuchElementException ex) { return Map.of("error", "NOT_FOUND", "message", ex.getMessage()); }
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> invalid(RuntimeException ex) { return Map.of("error", "INVALID_REQUEST", "message", ex.getMessage()); }
    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, String> forbidden(SecurityException ex) { return Map.of("error", "FORBIDDEN", "message", ex.getMessage()); }
}
