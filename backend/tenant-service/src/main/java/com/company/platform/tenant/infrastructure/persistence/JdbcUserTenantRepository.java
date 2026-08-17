package com.company.platform.tenant.infrastructure.persistence;

import com.company.platform.tenant.domain.usertenant.*; import org.springframework.jdbc.core.JdbcTemplate; import org.springframework.stereotype.Repository;
import java.sql.Timestamp; import java.util.Optional; import java.util.UUID; import java.util.List;

@Repository
public class JdbcUserTenantRepository implements UserTenantRepository {
    private final JdbcTemplate jdbc; public JdbcUserTenantRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    @Override public UserTenant save(UserTenant r){jdbc.update("INSERT INTO user_tenants(id,user_id,tenant_id,role,status,created_at,updated_at) VALUES (?,?,?,?,?,?,?) ON CONFLICT(user_id,tenant_id) DO UPDATE SET role=EXCLUDED.role,status=EXCLUDED.status,updated_at=EXCLUDED.updated_at",r.id(),r.userId(),r.tenantId(),r.role(),r.status(),Timestamp.from(r.createdAt()),Timestamp.from(r.createdAt()));return r;}
    @Override public Optional<UserTenant> findByUserIdAndTenantId(UUID userId,UUID tenantId){return jdbc.query("SELECT * FROM user_tenants WHERE user_id=? AND tenant_id=?",rs->{if(!rs.next())return Optional.empty();return Optional.of(new UserTenant(rs.getObject("id",UUID.class),rs.getObject("user_id",UUID.class),rs.getObject("tenant_id",UUID.class),rs.getString("role"),rs.getString("status"),rs.getTimestamp("created_at").toInstant()));},userId,tenantId);}
    @Override public List<UserTenant> findByTenantId(UUID tenantId){return jdbc.query("SELECT * FROM user_tenants WHERE tenant_id=? ORDER BY created_at",(rs,n)->new UserTenant(rs.getObject("id",UUID.class),rs.getObject("user_id",UUID.class),rs.getObject("tenant_id",UUID.class),rs.getString("role"),rs.getString("status"),rs.getTimestamp("created_at").toInstant()),tenantId);}
    @Override public void remove(UUID userId, UUID tenantId){jdbc.update("UPDATE user_tenants SET status='REMOVED',updated_at=now() WHERE user_id=? AND tenant_id=?",userId,tenantId);}
}
