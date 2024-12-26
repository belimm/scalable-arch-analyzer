package com.scalableanalyzer.microservice.product.controller;

import com.scalableanalyzer.microservice.product.dto.ProductDto;
import com.scalableanalyzer.microservice.product.repository.ProductRepository;
import com.scalableanalyzer.microservice.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservice")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        return productService.findAllProducts();
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(productDto);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> findProductById(@PathVariable("productId") Long productId){
        return productService.findProductById(productId);
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<?> findByCategoryCategoryId(@PathVariable("categoryId") Long categoryId){
        return productService.findProductsByCategoryCategoryId(categoryId);
    }






}
