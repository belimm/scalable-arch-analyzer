package com.scalableanalyzer.microservice.microbridge.controller;

import com.scalableanalyzer.microservice.microbridge.dto.OrderRequestDto;
import com.scalableanalyzer.microservice.microbridge.service.OrderService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservice")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

   /* @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<?> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequestDto orderRequestDto) {
        return orderService.updateOrder(orderId, orderRequestDto);
    }

    @GetMapping("/orders/{orderId}/total")
    public ResponseEntity<?> getOrderTotal(@PathVariable Long orderId) {
        return orderService.getOrderTotal(orderId);
    }
*/
}
