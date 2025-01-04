package com.scalableanalyzer.microservice.cart.service;

import com.scalableanalyzer.microservice.cart.dto.CartItemDto;
import com.scalableanalyzer.microservice.cart.entity.CartItem;
import org.springframework.http.ResponseEntity;

public interface ICartItemService {
    public ResponseEntity<?> addItem(Long cartId, CartItemDto cartItem);


}
