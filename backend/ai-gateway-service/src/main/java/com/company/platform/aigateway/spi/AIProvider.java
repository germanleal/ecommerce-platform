package com.company.platform.aigateway.spi;
public interface AIProvider { String id(); String version(); AIResponse generate(AIRequest request); }
