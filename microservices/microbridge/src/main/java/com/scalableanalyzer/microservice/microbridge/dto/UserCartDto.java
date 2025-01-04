package com.scalableanalyzer.microservice.microbridge.dto;

import java.util.List;

public class UserCartDto {

    private Long userId;
    private Long cartId;
    private List<CartItemDto> items;

    public UserCartDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }
}
