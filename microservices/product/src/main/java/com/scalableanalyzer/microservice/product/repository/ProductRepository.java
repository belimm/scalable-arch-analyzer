package com.scalableanalyzer.microservice.product.repository;

import com.scalableanalyzer.microservice.product.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findProductsByCategoryCategoryId(Long productId);
}
