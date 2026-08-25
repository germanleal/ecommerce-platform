package com.company.platform.administration.api;

import com.company.platform.administration.application.CatalogAdministrationService;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/admin")
public class CatalogAdministrationController {
 private static final Set<String> TYPES=Set.of("companies","categories","taxes","products","services"); private final CatalogAdministrationService service;
 public CatalogAdministrationController(CatalogAdministrationService service){this.service=service;}
 private String type(String value){if(!TYPES.contains(value))throw new IllegalArgumentException("unsupported resource");return value;}
 @GetMapping("/{resource:companies|categories|taxes|products|services}") public List<Map<String,Object>> list(@PathVariable String resource,@RequestParam(required=false)UUID tenantId,@RequestParam(defaultValue="")String search,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="25")int size,Authentication a){return service.list(type(resource),a,tenantId,search,page,size);}
 @GetMapping("/{resource:companies|categories|taxes|products|services}/{id}") public Map<String,Object> get(@PathVariable String resource,@PathVariable UUID id,Authentication a){return service.get(type(resource),id,a);}
 @PostMapping("/{resource:companies|categories|taxes|products|services}") @ResponseStatus(HttpStatus.CREATED) public Map<String,Object> create(@PathVariable String resource,@RequestBody Map<String,Object>b,Authentication a){return service.create(type(resource),b,a);}
 @PutMapping("/{resource:companies|categories|taxes|products|services}/{id}") public Map<String,Object> update(@PathVariable String resource,@PathVariable UUID id,@RequestBody Map<String,Object>b,Authentication a){return service.update(type(resource),id,b,a);}
 @PatchMapping("/{resource:companies|categories|taxes|products|services}/{id}/status") public Map<String,Object> status(@PathVariable String resource,@PathVariable UUID id,@RequestBody Map<String,String>b,Authentication a){return service.status(type(resource),id,b.get("status"),a);}
 @DeleteMapping("/products/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable UUID id,Authentication a){service.deleteProduct(id,a);}
 @GetMapping("/products/{id}/images") public List<Map<String,Object>> images(@PathVariable UUID id,Authentication a){return service.images(id,a);}
 @PostMapping("/products/{id}/images") @ResponseStatus(HttpStatus.CREATED) public Map<String,Object> addImage(@PathVariable UUID id,@RequestBody Map<String,Object>b,Authentication a){return service.addImage(id,b,a);}
 @PutMapping("/products/{id}/images/{imageId}") public Map<String,Object> updateImage(@PathVariable UUID id,@PathVariable UUID imageId,@RequestBody Map<String,Object>b,Authentication a){return service.updateImage(id,imageId,b,a);}
 @DeleteMapping("/products/{id}/images/{imageId}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteImage(@PathVariable UUID id,@PathVariable UUID imageId,Authentication a){service.deleteImage(id,imageId,a);}
 @GetMapping("/capabilities") public List<Map<String,Object>> capabilities(){return service.capabilities();}
 @GetMapping("/tenants/{id}/capabilities") public List<Map<String,Object>> tenantCapabilities(@PathVariable UUID id,Authentication a){return service.tenantCapabilities(id,a);}
 @PutMapping("/tenants/{id}/capabilities/{code}") public Map<String,Object> capability(@PathVariable UUID id,@PathVariable String code,@RequestBody Map<String,String>b,Authentication a){return service.setCapability(id,code,b.getOrDefault("status","ACTIVE"),a);}
}
