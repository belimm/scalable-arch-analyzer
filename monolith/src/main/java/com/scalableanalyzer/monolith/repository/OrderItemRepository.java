package com.scalableanalyzer.monolith.repository;

import com.scalableanalyzer.monolith.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

}
