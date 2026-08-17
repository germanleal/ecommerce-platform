package com.company.platform.inventory.application;
import com.company.platform.inventory.domain.*;import java.util.*;
public interface FulfillmentStore{Fulfillment save(Fulfillment f);Optional<Fulfillment> find(UUID t,UUID id);List<Fulfillment> findByOrder(UUID t,UUID order);PickingTask save(PickingTask p);PackingTask save(PackingTask p);}
