package com.company.platform.tenant.api;

import com.company.platform.tenant.domain.tenant.*; import org.springframework.http.*; import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TenantExceptionHandler {
    @ExceptionHandler(TenantAccessDeniedException.class) ResponseEntity<String> denied(TenantAccessDeniedException e){return ResponseEntity.status(HttpStatus.FORBIDDEN).body("TENANT_ACCESS_DENIED");}
    @ExceptionHandler(TenantNotFoundException.class) ResponseEntity<String> missing(TenantNotFoundException e){return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TENANT_NOT_FOUND");}
    @ExceptionHandler(InvalidTenantException.class) ResponseEntity<String> invalid(InvalidTenantException e){return ResponseEntity.badRequest().body("TENANT_MISMATCH");}
}
