package com.company.platform.inventory.domain;
import java.time.Instant; import java.util.UUID;
public record Warehouse(UUID id,UUID tenantId,String code,String name,boolean active,Instant createdAt){public Warehouse{if(tenantId==null||code==null||code.isBlank()||name==null||name.isBlank())throw new IllegalArgumentException("invalid warehouse");if(id==null)id=UUID.randomUUID();if(createdAt==null)createdAt=Instant.now();}public static Warehouse create(UUID t,String c,String n){return new Warehouse(null,t,c,n,true,null);}}
