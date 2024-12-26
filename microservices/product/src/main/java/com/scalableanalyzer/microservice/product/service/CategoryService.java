package com.scalableanalyzer.microservice.product.service;

import com.scalableanalyzer.microservice.product.dto.CategoryDto;
import com.scalableanalyzer.microservice.product.dto.ErrorDto;
import com.scalableanalyzer.microservice.product.entity.Category;
import com.scalableanalyzer.microservice.product.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<?> getAllCategories() {
        List<Category> category = (List<Category>) categoryRepository.findAll();

        ArrayList<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category categoryItem : category) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setCategoryDescription(categoryItem.getCategoryDescription());
            categoryDto.setCategoryName(categoryItem.getCategoryName());
            categoryDto.setCategoryId(categoryItem.getCategoryId());
            categoryDtos.add(categoryDto);
        }

        return ResponseEntity.ok().body(categoryDtos);
    }

    @Override
    public ResponseEntity<?> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);

        ErrorDto categoryError = new ErrorDto();


        if (category == null) {
            categoryError.setMessage("Category not found");
            categoryError.setErrorCode(1L);
            return ResponseEntity.status(404).body(categoryError);
        }

        return ResponseEntity.ok().body(toDto(category));
    }

    @Override
    public ResponseEntity<?> createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category newCategory  = categoryRepository.save(category);


        return ResponseEntity.ok().body(toDto(newCategory));
    }

    private CategoryDto toDto(Category category) {
            CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(category.getCategoryId());
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setCategoryDescription(category.getCategoryDescription());

        return categoryDto;
    }


}
