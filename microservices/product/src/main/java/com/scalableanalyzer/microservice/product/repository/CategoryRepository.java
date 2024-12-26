package com.scalableanalyzer.microservice.product.repository;

import com.scalableanalyzer.microservice.product.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
