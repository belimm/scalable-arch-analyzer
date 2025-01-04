package com.scalableanalyzer.monolith.service;

import com.scalableanalyzer.monolith.dto.CartItemDto;
import org.springframework.http.ResponseEntity;

public interface ICartItemService {
    public ResponseEntity<?> addItem(Long cartId, CartItemDto cartItem);


}
