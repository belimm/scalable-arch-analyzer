package com.scalableanalyzer.monolith.service;

import com.scalableanalyzer.monolith.dto.ProductDto;
import com.scalableanalyzer.monolith.entity.Category;
import com.scalableanalyzer.monolith.entity.Product;
import com.scalableanalyzer.monolith.repository.CategoryRepository;
import com.scalableanalyzer.monolith.repository.ProductRepository;
import com.scalableanalyzer.monolith.service.IProductService;
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
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = (List<Product>) productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product p:products) { //For dto conversion
            productDtos.add(toProductDto(p));
        }

        return ResponseEntity
                .ok()
                .body(productDtos);
    }

    @Override
    public ResponseEntity<?> addProduct(ProductDto product) {
        System.out.println(product);

        if(product.getCategoryId()==null && product.getCategoryName()==null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Empty fields enter categoryId or categoryName");
        }

        Optional<Category> category = Optional.empty();
        if(product.getCategoryId()!=null){
            System.out.println("categoryId not null");
            category = categoryRepository.findById(product.getCategoryId());

            System.out.println(category);

        }


        if(category.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format("There is no Category with ID %s",product.getCategoryId()));
        }

        product.setCategoryName(category.get().getCategoryName());

        System.out.println("Product added before");
        Product newProduct = productRepository.save(toEntityProduct(product));

        System.out.println("Product added after");
        return ResponseEntity
                .ok().
                body(toProductDto(newProduct));
    }

    @Override
    public ResponseEntity<?> getProductById(Long productId) {

        if(productId<=0) { //In case of Invalid ID
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid ID!");
        }

        Optional<Product> product = productRepository.findById(productId);

        if(product.isEmpty()) { //In case of if there is no Product with given ID
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format("There is no product with ID %s",productId));
        }

        return ResponseEntity
                .ok()
                .body(toProductDto(product.get()));
    }

    @Override
    public ResponseEntity<?> getProductsByCategoryId(Long categoryId) {
        if(categoryId<=0) { //In case of Invalid ID
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Invalid ID!");
        }

        List<Product> products = productRepository.findAllByCategoryCategoryId(categoryId);
        List<ProductDto> productDtos = new ArrayList<>();

        for(Product p:products) // For dto conversion
            productDtos.add(toProductDto(p));

        return ResponseEntity
                .ok()
                .body(productDtos);
    }

    @Override
    public ResponseEntity<?> findByProductName(String productName) {
        Optional<Product> productOptional = Optional.ofNullable(productRepository.findByProductName(productName));

        if(productOptional.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(String.format("There is no product with Name  %s",productName));
        }

        ProductDto productDto = toProductDto(productOptional.get());


        return ResponseEntity
                .ok()
                .body(productDto);
    }

    /**
     * It takes Category entity and maps to CategoryDto class
     * @param product
     * @return categoryDto
     */
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