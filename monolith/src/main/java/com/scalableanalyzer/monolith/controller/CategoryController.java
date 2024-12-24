package com.scalableanalyzer.monolith.controller;

import com.scalableanalyzer.monolith.entity.Category;
import com.scalableanalyzer.monolith.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monolith")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("categories")
    public ResponseEntity<?> getAllCategories(){
        return categoryService.getAllCategories();
    }

    /** HTTP Method -- URI
     *  GET         -- http://localhost:8081/inventory/category/{categoryId}
     *              -- Get Category by categoryId response type as CategoryDto
     */
    @GetMapping("category/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    /** HTTP Method -- URI
     *  POST        -- http://localhost:8081/inventory/category
     *              -- Add a New Category response type as CategoryDto
     */
    @PostMapping("category")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
}