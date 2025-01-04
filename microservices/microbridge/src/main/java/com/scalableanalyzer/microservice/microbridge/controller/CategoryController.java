package com.scalableanalyzer.microservice.microbridge.controller;

import com.scalableanalyzer.microservice.microbridge.dto.CategoryDto;
import com.scalableanalyzer.microservice.microbridge.service.CategoryService;


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
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto category){
        return categoryService.addCategory(category);
    }
}