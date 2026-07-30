package com.company.platform.tenant.infrastructure.persistence;

import com.company.platform.tenant.domain.tenant.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp; import java.time.Instant; import java.util.Map; import java.util.Optional;

@Repository
public class JdbcTenantRepository implements TenantRepository {
    private final JdbcTemplate jdbc;
    public JdbcTenantRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    @Override public Tenant save(Tenant t){ jdbc.update("INSERT INTO tenants(id,name,slug,status,configuration,created_at,updated_at) VALUES (?,?,?,?,?::jsonb,?,?) ON CONFLICT(id) DO UPDATE SET name=EXCLUDED.name,status=EXCLUDED.status,updated_at=EXCLUDED.updated_at", t.id().value(),t.name().value(),t.slug().value(),t.status().name(),"{}",Timestamp.from(t.createdAt()),Timestamp.from(t.updatedAt())); return t; }
    @Override public Optional<Tenant> findById(TenantId id){ return query("SELECT * FROM tenants WHERE id = ?",id.value()); }
    @Override public Optional<Tenant> findBySlug(Slug slug){ return query("SELECT * FROM tenants WHERE slug = ?",slug.value()); }
    private Optional<Tenant> query(String sql,Object arg){ return jdbc.query(sql,rs->{if(!rs.next()) return Optional.empty(); return Optional.of(Tenant.rehydrate(new TenantId(rs.getObject("id",java.util.UUID.class)),new TenantName(rs.getString("name")),new Slug(rs.getString("slug")),Map.of(),TenantStatus.valueOf(rs.getString("status")),rs.getTimestamp("created_at").toInstant(),rs.getTimestamp("updated_at").toInstant()));},arg); }
}
