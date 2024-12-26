package com.scalableanalyzer.microservice.product.controller;


import com.scalableanalyzer.microservice.product.dto.CategoryDto;
import com.scalableanalyzer.microservice.product.entity.Category;
import com.scalableanalyzer.microservice.product.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservice")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("categories")
    public ResponseEntity<?> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping("category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        return categoryService.createCategory(categoryDto);
    }


}
