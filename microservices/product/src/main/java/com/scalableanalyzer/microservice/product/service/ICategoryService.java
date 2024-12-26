package com.scalableanalyzer.microservice.product.service;

import com.scalableanalyzer.microservice.product.dto.CategoryDto;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {

    ResponseEntity<?> getAllCategories();

    ResponseEntity <?> getCategoryById(Long id);

    ResponseEntity<?> createCategory(CategoryDto categoryDto);

}
