package com.scalableanalyzer.monolith.repository;

import com.scalableanalyzer.monolith.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
