package com.scalableanalyzer.microservice.cart.repository;

import com.scalableanalyzer.microservice.cart.entity.Cart;
import com.scalableanalyzer.microservice.cart.entity.CartItem;
import com.scalableanalyzer.microservice.cart.entity.Product;


import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    CartItem findByCartAndProduct(Cart cart, Product product);
}
