package com.scalableanalyzer.microservice.cart.repository;

import com.scalableanalyzer.microservice.cart.entity.Cart;

import org.springframework.data.repository.CrudRepository;

public interface CartRepository extends CrudRepository<Cart, Long> {
    Cart findByUserCart_Id(Long id);
    Boolean existsByUserCart_Id(Long id);
}
