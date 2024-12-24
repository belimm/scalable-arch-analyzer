package com.scalableanalyzer.monolith.service;


import com.scalableanalyzer.monolith.dto.CategoryDto;
import com.scalableanalyzer.monolith.entity.Category;
import com.scalableanalyzer.monolith.repository.CategoryRepository;
import com.scalableanalyzer.monolith.service.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = (List<Category>) categoryRepository.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();

        for(Category c: categories) //For Dto Conversion
            categoryDtos.add(toDto(c));

        return ResponseEntity
                .ok()
                .body(categoryDtos);
    }

    @Override
    public ResponseEntity<?> getCategoryById(Long categoryId) {
        if(categoryId<=0) { //In case of invalid categoryId
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid ID!");
        }

        Optional<Category> category = categoryRepository.findById(categoryId);

        if(category.isEmpty()) { //If there is no category with given ID!
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format("There is no category with ID %s",categoryId));
        }

        return ResponseEntity
                .ok()
                .body(toDto((category.get())));
    }

    @Override
    public ResponseEntity<?> addCategory(Category category) {
        category.setProductList(new ArrayList<>());
        Category newCategory = categoryRepository.save(category);

        return ResponseEntity
                .ok()
                .body(toDto(newCategory));
    }

    /**
     * It takes Category entity and maps to CategoryDto class
     * @param category
     * @return categoryDto
     */
    public CategoryDto toDto(Category category){
        System.out.println(category);
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(category.getCategoryName());
        categoryDto.setCategoryId(category.getCategoryId());
        categoryDto.setCategoryDescription(category.getCategoryDescription());
        return categoryDto;
    }
}