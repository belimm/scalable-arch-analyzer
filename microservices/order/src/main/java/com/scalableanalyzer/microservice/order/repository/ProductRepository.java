package com.scalableanalyzer.microservice.order.repository;

import com.scalableanalyzer.microservice.order.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
