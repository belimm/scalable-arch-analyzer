package com.scalableanalyzer.monolith.service;

import com.scalableanalyzer.monolith.dto.OrderRequestDto;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    public ResponseEntity<?> createOrder(OrderRequestDto order);
    public ResponseEntity<?> getOrderById(Long id);
    public ResponseEntity<?> updateOrder(Long id, OrderRequestDto order);
    public ResponseEntity<?> getOrderTotal(Long id);
}
