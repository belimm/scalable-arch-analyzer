package com.scalableanalyzer.microservice.product.service;

import com.scalableanalyzer.microservice.product.dto.ProductDto;
import com.scalableanalyzer.microservice.product.entity.Product;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    ResponseEntity<?> createProduct(ProductDto product);
    ResponseEntity<?> deleteProduct(Long id);
    ResponseEntity<?> findProductById(Long id);
    ResponseEntity<?> findProductsByCategoryCategoryId(Long id);
    ResponseEntity<?> findAllProducts();
}
