package com.scalableanalyzer.microservice.product.service;

import com.scalableanalyzer.microservice.product.dto.ErrorDto;
import com.scalableanalyzer.microservice.product.dto.ProductDto;
import com.scalableanalyzer.microservice.product.entity.Category;
import com.scalableanalyzer.microservice.product.entity.Product;
import com.scalableanalyzer.microservice.product.repository.CategoryRepository;
import com.scalableanalyzer.microservice.product.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<?> createProduct(ProductDto product) {
        System.out.println(product);

        ErrorDto errorDto = new ErrorDto();

        if(product.getCategoryId()==null && product.getCategoryName()==null){
            errorDto.setErrorCode(2L);
            errorDto.setMessage("Empty fields enter categoryId or categoryName");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorDto);
        }

        Optional<Category> category = Optional.empty();
        if(product.getCategoryId()!=null){

            category = categoryRepository.findById(product.getCategoryId());



        }

        if(category.isEmpty()){
            errorDto.setErrorCode(3L);
            errorDto.setMessage(String.format("Category with id %s not found", product.getCategoryId()));
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
        product.setCategoryName(category.get().getCategoryName());

        Product newProduct = productRepository.save(toEntityProduct(product));

        return ResponseEntity
                .ok().
                body(toProductDto(newProduct));
    }



    @Override
    public ResponseEntity<?> deleteProduct(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<?> findProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        if(product==null){
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(4L);
            errorDto.setMessage(String.format("Product with id %s not found", id));

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
        }

        return ResponseEntity.ok().body(toProductDto(product));
    }

    @Override
    public ResponseEntity<?> findProductsByCategoryCategoryId(Long id) {
        List<Product> products = productRepository.findProductsByCategoryCategoryId(id);

        if(products.isEmpty()){
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(5L);
            errorDto.setMessage(String.format("Product with id %s not found", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
        }

        ArrayList<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products){
            ProductDto productDto = toProductDto(product);
            productDto.setProductImageUrl(product.getProductImageUrl());
            productDto.setProductId(product.getProductId());
            productDto.setCategoryId(product.getCategory().getCategoryId());
            productDto.setUpdatedAt(product.getUpdatedAt());
            productDto.setCreatedAt(product.getCreatedAt());
            productDto.setProductName(product.getProductName());
            productDto.setProductDescription(product.getProductDescription());
            productDto.setSalePrice(product.getSalesPrice());
            productDtos.add(productDto);
        }

        return ResponseEntity.ok().body(productDtos);
    }


    @Override
    public ResponseEntity<?> findAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
       if(products.isEmpty()){
           ErrorDto errorDto = new ErrorDto();
           errorDto.setErrorCode(5L);
           errorDto.setMessage(String.format("No products found"));
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
       }
       ArrayList<ProductDto> productDtos = new ArrayList<>();
       for(Product product : products){
           ProductDto productDto = toProductDto(product);
           productDto.setCategoryId(product.getCategory().getCategoryId());
           productDto.setProductDescription(product.getProductDescription());
           productDto.setProductName(product.getProductName());
           productDto.setProductImageUrl(product.getProductImageUrl());
           productDto.setCreatedAt(product.getCreatedAt());
           productDto.setSalePrice(product.getSalesPrice());
           productDto.setCategoryName(product.getCategory().getCategoryName());
           productDto.setCategoryId(product.getCategory().getCategoryId());
           productDto.setProductId(product.getProductId());
           productDtos.add(productDto);
       }

       return ResponseEntity.ok().body(productDtos);
    }

    private ProductDto toProductDto(Product product){
        ProductDto productDto = new ProductDto();

        productDto.setProductId(product.getProductId());
        productDto.setCategoryId(product.getCategory().getCategoryId());
        productDto.setProductName(product.getProductName());
        productDto.setCategoryName(product.getCategory().getCategoryName());
        productDto.setSalePrice(product.getSalesPrice());
        productDto.setProductImageUrl(product.getProductImageUrl());
        productDto.setProductDescription(product.getProductDescription());

        return productDto;
    }

    private Product toEntityProduct(ProductDto productDto){
        Product product = new Product();

        product.setProductId(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setSalesPrice(productDto.getSalePrice());
        Category category = new Category();
        category.setCategoryId(productDto.getCategoryId());
        category.setCategoryName(productDto.getCategoryName());
        product.setCategory(category);
        product.setProductDescription(productDto.getProductDescription());
        product.setProductImageUrl(productDto.getProductImageUrl());

        return product;
    }
}
