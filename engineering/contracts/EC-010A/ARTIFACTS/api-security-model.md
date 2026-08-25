# API security model

All non-health endpoints require an authenticated JWT. Orders enforce granular permissions with `@PreAuthorize`; Commerce protects product/price operations; Administration protects its controller with admin authorities. Resource Server validates issuer, signature and expiry through Spring Security.
