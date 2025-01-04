package com.scalableanalyzer.microservice.microbridge.util;

public enum ErrorCodes {
    PRODUCT_NOT_FOUND(1L, "Product not found"),
    CATEGORY_NOT_FOUND(2L, "Category not found"),
    USER_NOT_FOUND(3L, "User not found"),
    ADDRESS_NOT_FOUND(4L, "Address not found"),
    CART_NOT_FOUND(5L, "Cart not found"),
    CART_ITEM_NOT_FOUND(6L, "Cart item not found"),
    SERVER_ERROR(100L, "Server error");


    private Long errorCode;
    private String message;

    ErrorCodes() {
    }

    ErrorCodes(Long errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Long errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
