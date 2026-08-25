package com.company.platform.tenant.infrastructure.persistence;

import com.company.platform.tenant.domain.organization.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcOrganizationRepository implements OrganizationRepository {
    private final JdbcTemplate jdbc;
    public JdbcOrganizationRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    public Organization save(Organization o) {
        jdbc.update("INSERT INTO organizations(id,name,slug,status,created_at,updated_at) VALUES (?,?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET name=EXCLUDED.name,slug=EXCLUDED.slug,status=EXCLUDED.status,updated_at=EXCLUDED.updated_at", o.id(), o.name(), o.slug(), o.status().name(), Timestamp.from(o.createdAt()), Timestamp.from(o.updatedAt()));
        return o;
    }
    public Optional<Organization> findById(UUID id) { return query("SELECT * FROM organizations WHERE id=?", id); }
    public Optional<Organization> findBySlug(String slug) { return query("SELECT * FROM organizations WHERE slug=?", slug); }
    private Optional<Organization> query(String sql, Object arg) { return jdbc.query(sql, rs -> { if (!rs.next()) return Optional.empty(); return Optional.of(Organization.rehydrate(rs.getObject("id", UUID.class), rs.getString("name"), rs.getString("slug"), OrganizationStatus.valueOf(rs.getString("status")), rs.getTimestamp("created_at").toInstant(), rs.getTimestamp("updated_at").toInstant())); }, arg); }
}
