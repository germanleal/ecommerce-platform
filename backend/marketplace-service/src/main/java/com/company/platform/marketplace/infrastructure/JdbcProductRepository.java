package com.company.platform.marketplace.infrastructure;

import com.company.platform.marketplace.domain.product.*;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductRepository implements ProductRepository {
    private final JdbcTemplate jdbc;
    public JdbcProductRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }
    private Product map(java.sql.ResultSet r) throws java.sql.SQLException { return Product.rehydrate(r.getObject("id", UUID.class), r.getObject("tenant_id", UUID.class), r.getObject("store_id", UUID.class), r.getObject("catalog_id", UUID.class), r.getObject("category_id", UUID.class), new SKU(r.getString("sku")), new ProductName(r.getString("name")), r.getString("description"), ProductStatus.valueOf(r.getString("status"))); }
    public Product save(Product p) { jdbc.update("INSERT INTO products(id,tenant_id,store_id,catalog_id,category_id,sku,name,description,status) VALUES(?,?,?,?,?,?,?,?,?) ON CONFLICT(id) DO UPDATE SET status=EXCLUDED.status,updated_at=CURRENT_TIMESTAMP", p.id(),p.tenantId(),p.storeId(),p.catalogId(),p.categoryId(),p.sku().value(),p.name().value(),p.description(),p.status().name()); return p; }
    public Optional<Product> findByTenantIdAndId(UUID tenantId, UUID id) { return jdbc.query("SELECT * FROM products WHERE tenant_id=? AND id=?", (r,n)->map(r), tenantId,id).stream().findFirst(); }
    public List<Product> findAllByTenantId(UUID tenantId) { return jdbc.query("SELECT * FROM products WHERE tenant_id=? ORDER BY name", (r,n)->map(r), tenantId); }
}
