package com.company.platform.marketplace.api;

import com.company.platform.marketplace.application.MarketplaceApplicationService;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/products")
public class ProductController {
    private final MarketplaceApplicationService service; private final JdbcTemplate jdbc;
    public ProductController(MarketplaceApplicationService service, JdbcTemplate jdbc){this.service=service;this.jdbc=jdbc;}
    public record CreateProductRequest(UUID storeId,UUID catalogId,UUID categoryId,String sku,String name,String description) { }
    @PostMapping public Object create(@RequestBody CreateProductRequest r){return service.createProduct(r.storeId(),r.catalogId(),r.categoryId(),r.sku(),r.name(),r.description());}
    // The administration catalog is the source of truth for the public marketplace.
    private static final String ACTIVE_PRODUCTS =
        "SELECT ap.id, ap.tenant_id, ap.company_id AS store_id, NULL::uuid AS catalog_id, ap.category_id, ap.sku, ap.name, ap.description, ap.status, " +
        "ac.name AS category_name, comp.commercial_name AS store_name, ap.amount, ap.amount AS net_price, ap.final_price, " +
        "COALESCE(tax.rate,0) AS tax_rate, ap.final_price-ap.amount AS tax_amount, ap.currency, img.url AS image_url " +
        "FROM administration.administration_products ap " +
        "LEFT JOIN administration.administration_categories ac ON ac.id=ap.category_id " +
        "JOIN administration.administration_companies comp ON comp.id=ap.company_id AND comp.status='ACTIVE' " +
        "LEFT JOIN administration.administration_taxes tax ON tax.id=ap.tax_id " +
        "LEFT JOIN LATERAL (SELECT url FROM administration.administration_product_images WHERE product_id=ap.id AND is_primary=true AND status='ACTIVE' ORDER BY sort_order LIMIT 1) img ON true " +
        "WHERE ap.status='ACTIVE' AND ap.deleted_at IS NULL";
    @GetMapping public List<Map<String,Object>> list(@RequestParam(required=false) UUID storeId,@RequestParam(required=false) String search){StringBuilder sql=new StringBuilder("SELECT * FROM (").append(ACTIVE_PRODUCTS).append(") catalog WHERE 1=1");List<Object> args=new ArrayList<>();if(storeId!=null){sql.append(" AND store_id=?");args.add(storeId);}if(search!=null&&!search.isBlank()){sql.append(" AND (LOWER(name) LIKE LOWER(?) OR LOWER(COALESCE(description,'')) LIKE LOWER(?))");args.add("%"+search.trim()+"%");args.add("%"+search.trim()+"%");}sql.append(" ORDER BY name");return jdbc.queryForList(sql.toString(),args.toArray());}
    @GetMapping("/{id}") public Map<String,Object> get(@PathVariable UUID id){return jdbc.queryForList("SELECT * FROM ("+ACTIVE_PRODUCTS+") catalog WHERE id=?",id).stream().findFirst().orElseThrow(()->new NoSuchElementException("product not found"));}
    @PostMapping("/{id}/publish") @ResponseStatus(HttpStatus.OK) public Object publish(@PathVariable UUID id){return service.publishProduct(id);}
}
