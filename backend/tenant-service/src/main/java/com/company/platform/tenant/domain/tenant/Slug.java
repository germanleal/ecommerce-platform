package com.company.platform.tenant.domain.tenant;

import java.util.Locale;

public record Slug(String value) {
    public Slug { if (value == null || !value.matches("[a-z0-9]+(?:-[a-z0-9]+)*")) throw new IllegalArgumentException("invalid slug"); value = value.toLowerCase(Locale.ROOT); }
}
