package com.company.platform.inventory.application;
import com.company.platform.inventory.domain.Inventory;import java.util.*;
public interface InventoryStore{Inventory save(Inventory i);Optional<Inventory> findInventory(UUID tenantId,UUID id);List<Inventory> findByProduct(UUID tenantId,UUID productId);}
