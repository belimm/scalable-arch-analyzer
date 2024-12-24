package com.scalableanalyzer.monolith.repository;

import com.scalableanalyzer.monolith.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAllByCategoryCategoryId(Long categoryId);
    Product findByProductName(String productName);
}
