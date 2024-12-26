package com.scalableanalyzer.microservice.order.repository;

import com.scalableanalyzer.microservice.order.entity.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
