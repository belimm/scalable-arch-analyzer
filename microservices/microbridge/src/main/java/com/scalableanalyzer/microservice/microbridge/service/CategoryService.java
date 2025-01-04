package com.scalableanalyzer.microservice.microbridge.service;

import com.scalableanalyzer.microservice.microbridge.dto.CategoryDto;
import com.scalableanalyzer.microservice.microbridge.dto.ErrorDto;
import com.scalableanalyzer.microservice.microbridge.dto.ProductDto;
import com.scalableanalyzer.microservice.microbridge.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class CategoryService {
    private final RestTemplate restTemplate;

    public CategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> getAllCategories(){
        String uri = "http://localhost:8082/microservice/categories";
        try {
            List<CategoryDto> categories = restTemplate.getForObject(uri, List.class);

            return ResponseEntity
                    .ok()
                    .body(categories);
        } catch (Exception e) {
            System.err.println("An error occurred while fetching products: " + e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    public ResponseEntity<?> getCategoryById(Long categoryId){
        String uri = "http://localhost:8082/microservice/category/"+categoryId;

        System.out.println(uri);

        try {
            CategoryDto category = restTemplate.getForObject(uri, CategoryDto.class);

            return ResponseEntity
                    .ok()
                    .body(category);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching category: " + e.getMessage());
            ErrorCodes errorCode = ErrorCodes.CATEGORY_NOT_FOUND; // Replace with a more appropriate error code

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());


            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }

    public ResponseEntity<?> addCategory(CategoryDto categoryDto){
        String uri = "http://localhost:8082/microservice/category";

        try {
            CategoryDto response = restTemplate.postForObject(uri,categoryDto,CategoryDto.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        }
        catch (Exception er){ //In case of empty fields

            ErrorCodes errorCode = ErrorCodes.SERVER_ERROR;


            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());
            return ResponseEntity
                    .ok()
                    .body(errorDto);
        }
    }
}
