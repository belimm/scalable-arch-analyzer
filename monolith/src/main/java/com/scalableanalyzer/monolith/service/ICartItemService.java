package com.scalableanalyzer.monolith.service;

import com.scalableanalyzer.monolith.dto.CartItemDto;
import com.scalableanalyzer.monolith.entity.CartItem;
import org.springframework.http.ResponseEntity;

public interface ICartItemService {
    public ResponseEntity<?> addItem(Long cartId, CartItemDto cartItem);


}
