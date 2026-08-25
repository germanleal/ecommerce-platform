package com.company.platform.aigateway.orchestration;import java.util.*;public record PluginDescriptor(UUID id,UUID tenantId,String name,String version,String status,Map<String,Object> metadata){}
