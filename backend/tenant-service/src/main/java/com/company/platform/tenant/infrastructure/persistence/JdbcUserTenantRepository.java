package com.company.platform.tenant.infrastructure.persistence;

import com.company.platform.tenant.domain.usertenant.*; import org.springframework.jdbc.core.JdbcTemplate; import org.springframework.stereotype.Repository;
import java.sql.Timestamp; import java.util.Optional; import java.util.UUID;

@Repository
public class JdbcUserTenantRepository implements UserTenantRepository {
    private final JdbcTemplate jdbc; public JdbcUserTenantRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
    @Override public UserTenant save(UserTenant r){jdbc.update("INSERT INTO user_tenants(id,user_id,tenant_id,role,status,created_at) VALUES (?,?,?,?,?,?) ON CONFLICT(user_id,tenant_id) DO UPDATE SET role=EXCLUDED.role,status=EXCLUDED.status",r.id(),r.userId(),r.tenantId(),r.role(),r.status(),Timestamp.from(r.createdAt()));return r;}
    @Override public Optional<UserTenant> findByUserIdAndTenantId(UUID userId,UUID tenantId){return jdbc.query("SELECT * FROM user_tenants WHERE user_id=? AND tenant_id=?",rs->{if(!rs.next())return Optional.empty();return Optional.of(new UserTenant(rs.getObject("id",UUID.class),rs.getObject("user_id",UUID.class),rs.getObject("tenant_id",UUID.class),rs.getString("role"),rs.getString("status"),rs.getTimestamp("created_at").toInstant()));},userId,tenantId);}
}
