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
    @GetMapping public List<Map<String,Object>> list(@RequestParam(required=false) UUID storeId,@RequestParam(required=false) String search){StringBuilder sql=new StringBuilder("SELECT p.*, c.name AS category_name FROM products p LEFT JOIN categories c ON c.id=p.category_id WHERE p.status='ACTIVE'");List<Object> args=new ArrayList<>();if(storeId!=null){sql.append(" AND p.store_id=?");args.add(storeId);}if(search!=null&&!search.isBlank()){sql.append(" AND (LOWER(p.name) LIKE LOWER(?) OR LOWER(COALESCE(p.description,'')) LIKE LOWER(?))");args.add("%"+search.trim()+"%");args.add("%"+search.trim()+"%");}sql.append(" ORDER BY p.name");return jdbc.queryForList(sql.toString(),args.toArray());}
    @GetMapping("/{id}") public Map<String,Object> get(@PathVariable UUID id){return jdbc.queryForList("SELECT p.*, c.name AS category_name FROM products p LEFT JOIN categories c ON c.id=p.category_id WHERE p.id=? AND p.status='ACTIVE'",id).stream().findFirst().orElseThrow(()->new NoSuchElementException("product not found"));}
    @PostMapping("/{id}/publish") @ResponseStatus(HttpStatus.OK) public Object publish(@PathVariable UUID id){return service.publishProduct(id);}
}
