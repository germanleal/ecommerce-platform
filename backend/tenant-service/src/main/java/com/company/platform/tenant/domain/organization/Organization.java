package com.company.platform.tenant.domain.organization;

import java.time.Instant;
import java.util.UUID;

public final class Organization {
    private final UUID id;
    private String name;
    private String slug;
    private OrganizationStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    private Organization(UUID id, String name, String slug, OrganizationStatus status, Instant createdAt, Instant updatedAt) {
        if (id == null || name == null || name.isBlank() || slug == null || slug.isBlank()) throw new IllegalArgumentException("invalid organization");
        this.id = id; this.name = name; this.slug = slug; this.status = status; this.createdAt = createdAt; this.updatedAt = updatedAt;
    }
    public static Organization create(String name, String slug) { var now = Instant.now(); return new Organization(UUID.randomUUID(), name, slug, OrganizationStatus.ACTIVE, now, now); }
    public static Organization rehydrate(UUID id, String name, String slug, OrganizationStatus status, Instant createdAt, Instant updatedAt) { return new Organization(id, name, slug, status, createdAt, updatedAt); }
    public void suspend() { transition(OrganizationStatus.SUSPENDED); }
    public void update(String name, String slug) { if (status == OrganizationStatus.DEACTIVATED) throw new IllegalStateException("organization is deactivated"); if (name == null || name.isBlank() || slug == null || slug.isBlank()) throw new IllegalArgumentException("invalid organization"); this.name=name; this.slug=slug; touch(); }
    public void activate() { if (status != OrganizationStatus.SUSPENDED) throw new IllegalStateException("invalid organization state transition from " + status); status = OrganizationStatus.ACTIVE; touch(); }
    public void deactivate() { if (status == OrganizationStatus.DEACTIVATED) throw new IllegalStateException("organization is deactivated"); status = OrganizationStatus.DEACTIVATED; touch(); }
    private void transition(OrganizationStatus next) { if (status != OrganizationStatus.ACTIVE) throw new IllegalStateException("invalid organization state transition from " + status); status = next; touch(); }
    private void touch() { updatedAt = Instant.now(); }
    public UUID id(){return id;} public String name(){return name;} public String slug(){return slug;} public OrganizationStatus status(){return status;} public Instant createdAt(){return createdAt;} public Instant updatedAt(){return updatedAt;}
}
