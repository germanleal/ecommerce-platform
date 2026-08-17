package com.company.platform.marketplace.api;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/stores") public class StoreController {
 private final JdbcTemplate jdbc; public StoreController(JdbcTemplate jdbc){this.jdbc=jdbc;}
 @GetMapping public List<Map<String,Object>> list(@RequestParam(required=false) String search){String sql="SELECT id,tenant_id,name,slug,status,configuration FROM public.stores WHERE status='ACTIVE'";if(search!=null&&!search.isBlank())sql+=" AND (LOWER(name) LIKE LOWER(?) OR LOWER(slug) LIKE LOWER(?))";sql+=" ORDER BY name";Object[] args=search==null||search.isBlank()?new Object[]{}:new Object[]{"%"+search.trim()+"%","%"+search.trim()+"%"};return jdbc.queryForList(sql,args);}
 @GetMapping("/{id}") public Map<String,Object> get(@PathVariable UUID id){return jdbc.queryForList("SELECT id,tenant_id,name,slug,status,configuration FROM public.stores WHERE id=? AND status='ACTIVE'",id).stream().findFirst().orElseThrow(()->new NoSuchElementException("store not found"));}
}
