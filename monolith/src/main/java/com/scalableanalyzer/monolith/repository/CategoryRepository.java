package com.scalableanalyzer.monolith.repository;

import com.scalableanalyzer.monolith.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
