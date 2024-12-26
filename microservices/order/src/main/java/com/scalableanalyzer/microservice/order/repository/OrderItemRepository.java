package com.scalableanalyzer.microservice.order.repository;

import com.scalableanalyzer.microservice.order.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
