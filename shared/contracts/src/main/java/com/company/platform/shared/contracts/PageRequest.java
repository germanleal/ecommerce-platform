package com.company.platform.shared.contracts;

public record PageRequest(int page, int size, String sort) {
    public PageRequest { if (page < 0) throw new IllegalArgumentException("page must be zero or greater"); if (size < 1 || size > 200) throw new IllegalArgumentException("size must be between 1 and 200"); sort = sort == null ? "" : sort; }
}
