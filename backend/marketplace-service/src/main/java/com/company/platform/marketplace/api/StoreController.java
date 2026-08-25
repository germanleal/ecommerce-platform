package com.company.platform.marketplace.api;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/stores") public class StoreController {
 private final JdbcTemplate jdbc; public StoreController(JdbcTemplate jdbc){this.jdbc=jdbc;}
 // The administration company catalog is the source of truth for public sellers.
 // public.stores is infrastructure retained for legacy catalogs and must not leak into this listing.
 private static final String ACTIVE_COMPANIES =
  "SELECT id, tenant_id, commercial_name AS name, " +
  "lower(trim(both '-' from regexp_replace(commercial_name,'[^a-zA-Z0-9]+','-','g'))) AS slug, " +
  "status, '{}'::jsonb AS configuration " +
  "FROM administration.administration_companies WHERE status='ACTIVE'";
 @GetMapping public List<Map<String,Object>> list(@RequestParam(required=false) String search){StringBuilder sql=new StringBuilder("SELECT * FROM (").append(ACTIVE_COMPANIES).append(") stores WHERE 1=1");List<Object> args=new ArrayList<>();if(search!=null&&!search.isBlank()){sql.append(" AND (LOWER(name) LIKE LOWER(?) OR LOWER(slug) LIKE LOWER(?))");args.add("%"+search.trim()+"%");args.add("%"+search.trim()+"%");}sql.append(" ORDER BY name");return jdbc.queryForList(sql.toString(),args.toArray());}
 @GetMapping("/{id}") public Map<String,Object> get(@PathVariable UUID id){return jdbc.queryForList("SELECT * FROM ("+ACTIVE_COMPANIES+") stores WHERE id=?",id).stream().findFirst().orElseThrow(()->new NoSuchElementException("store not found"));}
}
