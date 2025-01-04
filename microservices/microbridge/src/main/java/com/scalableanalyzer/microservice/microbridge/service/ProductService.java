package com.scalableanalyzer.microservice.microbridge.service;

import com.scalableanalyzer.microservice.microbridge.dto.CategoryDto;
import com.scalableanalyzer.microservice.microbridge.dto.ErrorDto;
import com.scalableanalyzer.microservice.microbridge.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ProductService {
    private final RestTemplate restTemplate;

    public ProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> getAllProducts() {
        String uri = "http://localhost:8082/microservice/products";
        System.out.println(uri);
        try {
            List<ProductDto> products = restTemplate.getForObject(uri, List.class);

            System.out.println(products);

            return ResponseEntity
                    .ok()
                    .body(products);
        } catch (Exception e) {
            System.err.println("An error occurred while fetching products: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }


    public ResponseEntity<?> addProduct(ProductDto productDto) {

        String uri = "http://localhost:8082/microservice/product";

        try {
            ProductDto response = restTemplate.postForObject(uri,productDto,ProductDto.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        }
        catch (HttpMessageNotReadableException er){ //In case of empty fields
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(er.getMostSpecificCause().getMessage());
            errorDto.setErrorCode(1L);
            return ResponseEntity
                    .ok()
                    .body(errorDto);
        }
    }




    public ResponseEntity<?> findProductById(Long productId) {
        String uri = "http://localhost:8082/microservice/products/"+productId;

        System.out.println(uri);

        try {
            ProductDto product = restTemplate.getForObject(uri, ProductDto.class);

            return ResponseEntity
                    .ok()
                    .body(product);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching product: " + e.getMessage());
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage("An error occurred while fetching products: " );
            errorDto.setErrorCode(1L);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }


    public ResponseEntity<?> findByCategoryCategoryId(Long categoryId) {

        String uri = "http://localhost:8082/microservice/products/category/"+categoryId;

        System.out.println(uri);

        try {
            List<ProductDto> products = restTemplate.getForObject(uri, List.class);

            return ResponseEntity
                    .ok()
                    .body(products);
        } catch (Exception e) {

            System.out.println("An error occurred while fetching product: " + e.getMessage());
            ErrorDto errorDto = new ErrorDto();

            errorDto.setMessage(String.format("Product with id %s not found", categoryId));
            errorDto.setErrorCode(5L);

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }





}
