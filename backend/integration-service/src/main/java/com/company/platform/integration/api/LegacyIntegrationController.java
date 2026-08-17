package com.company.platform.integration.api;

import com.company.platform.integration.application.IntegrationPlatformService;
import com.company.platform.integration.domain.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integration")
public class LegacyIntegrationController {
    private final IntegrationPlatformService service;
    public LegacyIntegrationController(IntegrationPlatformService service) { this.service = service; }
    public record ConnectorRequest(String name,String code,String provider,String version,String direction,String protocol,String configuration) {}
    public record UpdateRequest(String name,String configuration) {}
    public record JobRequest(UUID connectorId,String executionType) {}
    @PostMapping("/connectors") public Connector register(@RequestBody ConnectorRequest r) { return service.register(r.name(),r.code(),r.provider(),r.version(),r.direction(),r.protocol(),r.configuration()); }
    @PutMapping("/connectors/{id}") public Connector update(@PathVariable UUID id,@RequestBody UpdateRequest r) { return service.update(id,r.name(),r.configuration()); }
    @GetMapping("/connectors") public List<Connector> connectors() { return service.connectors(); }
    @GetMapping("/connectors/{id}") public Connector connector(@PathVariable UUID id) { return service.get(id); }
    @PostMapping("/connectors/{id}/enable") public Connector enable(@PathVariable UUID id) { return service.enable(id); }
    @PostMapping("/connectors/{id}/disable") public Connector disable(@PathVariable UUID id) { return service.disable(id); }
    @PostMapping("/jobs") public IntegrationJob execute(@RequestBody JobRequest r) { return service.execute(r.connectorId(),r.executionType()); }
    @GetMapping("/jobs") public List<IntegrationJob> jobs() { return service.jobs(); }
    @GetMapping("/jobs/{id}") public IntegrationJob job(@PathVariable UUID id) { return service.getJob(id); }
    @PostMapping("/jobs/{id}/retry") public IntegrationJob retry(@PathVariable UUID id) { return service.retry(id); }
    @PostMapping("/jobs/{id}/cancel") public IntegrationJob cancel(@PathVariable UUID id) { return service.cancel(id); }
}
