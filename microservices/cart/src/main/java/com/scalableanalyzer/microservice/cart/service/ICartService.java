package com.scalableanalyzer.microservice.cart.service;

import org.springframework.http.ResponseEntity;

public interface ICartService {
    public ResponseEntity<?> createCart(Long userId);

    public ResponseEntity<?> deleteCart(Long userId);
}
