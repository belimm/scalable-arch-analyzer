package com.scalableanalyzer.monolith.repository;

import com.scalableanalyzer.monolith.entity.Cart;
import com.scalableanalyzer.monolith.entity.CartItem;
import com.scalableanalyzer.monolith.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    CartItem findByCartAndProduct(Cart cart, Product product);
}
