package com.scalableanalyzer.microservice.cart.repository;

import com.scalableanalyzer.microservice.cart.entity.Product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAllByCategoryCategoryId(Long categoryId);
    Product findByProductName(String productName);
}
