package com.company.platform.aigateway.application;
public class AIException extends RuntimeException { private final String code; private final int status; public AIException(String code, int status, String message) { super(message); this.code=code; this.status=status; } public String code(){return code;} public int status(){return status;} }
