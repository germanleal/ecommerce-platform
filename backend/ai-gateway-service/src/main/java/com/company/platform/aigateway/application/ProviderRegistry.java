package com.company.platform.aigateway.application;

import com.company.platform.aigateway.spi.AIProvider;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProviderRegistry {
    private final Map<String, AIProvider> providers = new HashMap<>();
    @Autowired public ProviderRegistry(List<AIProvider> available) { available.forEach(this::register); }
    public void register(AIProvider provider) { if (provider == null) throw new IllegalArgumentException("provider required"); providers.put(provider.id(), provider); }
    public Optional<AIProvider> find(String id) { return Optional.ofNullable(providers.get(id)); }
    public List<String> ids() { return providers.keySet().stream().sorted().toList(); }
}
