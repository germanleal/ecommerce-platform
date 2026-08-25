package com.company.platform.tenant.api;

import com.company.platform.tenant.domain.tenant.*; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TenantExceptionHandler {
    @ExceptionHandler(TenantAccessDeniedException.class) ResponseEntity<ApiError> denied(TenantAccessDeniedException e){return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiError("TENANT_ACCESS_DENIED", e.getMessage()));}
    @ExceptionHandler(TenantNotFoundException.class) ResponseEntity<ApiError> missing(TenantNotFoundException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiError("TENANT_NOT_FOUND", e.getMessage()));}
    @ExceptionHandler(InvalidTenantException.class) ResponseEntity<ApiError> invalid(InvalidTenantException e){return ResponseEntity.badRequest().body(new ApiError("TENANT_MISMATCH", e.getMessage()));}
    @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<ApiError> badRequest(IllegalArgumentException e){return ResponseEntity.badRequest().body(new ApiError("INVALID_REQUEST", e.getMessage()));}
    record ApiError(String code, String message) { }
}
