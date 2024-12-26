package com.scalableanalyzer.microservice.order.service;

import com.scalableanalyzer.microservice.order.dto.OrderRequestDto;
import org.springframework.http.ResponseEntity;

public interface IOrderService {
    public ResponseEntity<?> createOrder(OrderRequestDto order);
    public ResponseEntity<?> getOrderById(Long id);
    public ResponseEntity<?> updateOrder(Long id, OrderRequestDto order);
    public ResponseEntity<?> getOrderTotal(Long id);
}
