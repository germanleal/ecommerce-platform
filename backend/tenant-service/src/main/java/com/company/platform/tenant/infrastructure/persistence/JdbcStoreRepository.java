package com.company.platform.tenant.infrastructure.persistence;

import com.company.platform.tenant.domain.store.*; import com.company.platform.tenant.domain.tenant.Slug;
import org.springframework.jdbc.core.JdbcTemplate; import org.springframework.stereotype.Repository;
import java.sql.Timestamp; import java.util.List; import java.util.Map; import java.util.UUID;

@Repository
public class JdbcStoreRepository implements StoreRepository {
    private final JdbcTemplate jdbc; public JdbcStoreRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    @Override public Store save(Store s){jdbc.update("INSERT INTO stores(id,tenant_id,name,slug,status,configuration,created_at,updated_at) VALUES (?,?,?,?,?,?::jsonb,?,?) ON CONFLICT(id) DO UPDATE SET status=EXCLUDED.status,updated_at=EXCLUDED.updated_at",s.id(),s.tenantId(),s.name(),s.slug().value(),s.status().name(),"{}",Timestamp.from(s.createdAt()),Timestamp.from(s.updatedAt()));return s;}
    @Override public List<Store> findByTenantId(UUID tenantId){return jdbc.query("SELECT * FROM stores WHERE tenant_id = ? ORDER BY name",(rs,n)->Store.rehydrate(rs.getObject("id",UUID.class),rs.getObject("tenant_id",UUID.class),rs.getString("name"),new Slug(rs.getString("slug")),Map.of(),StoreStatus.valueOf(rs.getString("status")),rs.getTimestamp("created_at").toInstant()),tenantId);}
}
