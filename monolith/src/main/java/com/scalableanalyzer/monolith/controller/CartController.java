package com.scalableanalyzer.monolith.controller;

import com.scalableanalyzer.monolith.dto.CartItemDto;
import com.scalableanalyzer.monolith.entity.CartItem;
import com.scalableanalyzer.monolith.service.CartItemService;
import com.scalableanalyzer.monolith.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monolith/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;


    public CartController(CartService cartService, CartItemService cartItemService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> createCart(@PathVariable("userId") Long userId) {
        return cartService.createCart(userId);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable("cartId") Long cartId) {
        return cartService.deleteCart(cartId);
    }

    @PostMapping("/{cartId}/item")
    public ResponseEntity<?> addItemToCart(@PathVariable("cartId") Long cartId, @RequestBody CartItemDto cartItem) {

        return cartItemService.addItem(cartId, cartItem);
    }

    @DeleteMapping("/{cartId}/{cartItemId}")
    public ResponseEntity<?> deleteCartItemFromCart(@PathVariable("cartId") Long cartId, @PathVariable("cartItemId") Long cartItemId) {
        return cartItemService.deleteCartItemFromCart(cartId, cartItemId);
    }
}
