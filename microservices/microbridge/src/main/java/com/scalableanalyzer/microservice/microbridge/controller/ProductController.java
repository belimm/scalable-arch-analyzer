package com.scalableanalyzer.microservice.microbridge.controller;

import com.scalableanalyzer.microservice.microbridge.dto.ProductDto;
import com.scalableanalyzer.microservice.microbridge.service.ProductService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservice")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /** HTTP Method -- URI
     *  GET         -- http://localhost:8081/inventory/products
     *              -- Get Products by productId response type as ProductDto
     */
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts(){
        return productService.getAllProducts();
    }

    /** HTTP Method -- URI
     *  PUT         -- http://localhost:8081/inventory/product
     *              -- Add a new product response type as List of ProductDto
     */
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto){
        return productService.addProduct(productDto);
    }

    /** HTTP Method -- URI
     *  GET         -- http://localhost:8081/inventory/products/{categoryId}
     *              -- Get products with categoryId response type as List of ProductDto
     */
    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<?> findByCategoryCategoryId(@PathVariable("categoryId") Long categoryId){
        return productService.findByCategoryCategoryId(categoryId);
    }

    /** HTTP Method -- URI
     *  GET         -- http://localhost:8081/inventory/product/{productId}
     *              -- Get products with productId response type as ProductDto
     */
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> findProductById(@PathVariable("productId") Long productId) {
        return productService.findProductById(productId);
    }
}