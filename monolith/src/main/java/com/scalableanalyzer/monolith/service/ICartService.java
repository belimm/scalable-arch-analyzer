package com.scalableanalyzer.monolith.service;

import org.springframework.http.ResponseEntity;

public interface ICartService {
    public ResponseEntity<?> createCart(Long userId);

    public ResponseEntity<?> deleteCart(Long userId);
}
