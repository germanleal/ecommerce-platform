package com.company.platform.tenant.infrastructure.persistence;

import com.company.platform.tenant.domain.tenant.*;
import org.springframework.jdbc.core.JdbcTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp; import java.time.Instant; import java.util.Map; import java.util.Optional;

@Repository
public class JdbcTenantRepository implements TenantRepository {
    private final JdbcTemplate jdbc; private final ObjectMapper mapper;
    public JdbcTenantRepository(JdbcTemplate jdbc, ObjectMapper mapper){this.jdbc=jdbc;this.mapper=mapper;}
    @Override public Tenant save(Tenant t){ try { var json=mapper.writeValueAsString(t.configuration()); jdbc.update("INSERT INTO tenants(id,organization_id,name,slug,status,configuration,created_at,updated_at) VALUES (?,?,?,?,?,?::jsonb,?,?) ON CONFLICT(id) DO UPDATE SET organization_id=EXCLUDED.organization_id,name=EXCLUDED.name,slug=EXCLUDED.slug,status=EXCLUDED.status,configuration=EXCLUDED.configuration,updated_at=EXCLUDED.updated_at", t.id().value(),t.organizationId(),t.name().value(),t.slug().value(),t.status().name(),json,Timestamp.from(t.createdAt()),Timestamp.from(t.updatedAt())); return t; } catch(Exception e){throw new IllegalStateException("cannot persist tenant configuration",e);} }
    @Override public Optional<Tenant> findById(TenantId id){ return query("SELECT * FROM tenants WHERE id = ?",id.value()); }
    @Override public Optional<Tenant> findBySlug(Slug slug){ return query("SELECT * FROM tenants WHERE slug = ?",slug.value()); }
    @Override public Optional<Tenant> findByOrganizationAndSlug(java.util.UUID organizationId, Slug slug){ return query("SELECT * FROM tenants WHERE organization_id = ? AND slug = ?",organizationId,slug.value()); }
    private Optional<Tenant> query(String sql,Object... args){ return jdbc.query(sql,rs->{if(!rs.next()) return Optional.empty(); try { var config=mapper.readValue(rs.getString("configuration"),new TypeReference<Map<String,Object>>(){}); return Optional.of(Tenant.rehydrate(new TenantId(rs.getObject("id",java.util.UUID.class)),rs.getObject("organization_id",java.util.UUID.class),new TenantName(rs.getString("name")),new Slug(rs.getString("slug")),config,TenantStatus.valueOf(rs.getString("status")),rs.getTimestamp("created_at").toInstant(),rs.getTimestamp("updated_at").toInstant())); } catch(Exception e){throw new IllegalStateException("cannot read tenant configuration",e);} },args); }
}
