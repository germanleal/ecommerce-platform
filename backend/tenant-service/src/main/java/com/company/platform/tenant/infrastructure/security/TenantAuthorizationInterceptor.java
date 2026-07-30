package com.company.platform.tenant.infrastructure.security;

import com.company.platform.tenant.infrastructure.security.context.TenantContextService;
import org.aspectj.lang.ProceedingJoinPoint; import org.aspectj.lang.annotation.*; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component;

@Aspect @Component
public class TenantAuthorizationInterceptor {
    private final TenantContextService context; private final TenantAccessValidator validator;
    public TenantAuthorizationInterceptor(TenantContextService context, TenantAccessValidator validator){this.context=context;this.validator=validator;}
    @Around("@annotation(requiresTenant) || @within(requiresTenant) || @annotation(requiresPermission) || @within(requiresPermission)")
    public Object authorize(ProceedingJoinPoint point, RequiresTenant requiresTenant, RequiresPermission requiresPermission) throws Throwable {
        var tenantId = context.getCurrentTenantId(); var auth = SecurityContextHolder.getContext().getAuthentication();
        validator.validate(auth, tenantId, requiresPermission == null ? null : requiresPermission.value()); return point.proceed();
    }
}
