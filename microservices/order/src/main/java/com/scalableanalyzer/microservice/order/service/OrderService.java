package com.scalableanalyzer.microservice.order.service;

import com.scalableanalyzer.microservice.order.dto.ErrorDto;
import com.scalableanalyzer.microservice.order.dto.OrderItemDto;
import com.scalableanalyzer.microservice.order.dto.OrderRequestDto;
import com.scalableanalyzer.microservice.order.dto.OrderSummaryDto;
import com.scalableanalyzer.microservice.order.entity.*;
import com.scalableanalyzer.microservice.order.repository.AddressRepository;
import com.scalableanalyzer.microservice.order.repository.OrderRepository;
import com.scalableanalyzer.microservice.order.repository.ProductRepository;
import com.scalableanalyzer.microservice.order.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public ResponseEntity<?> createOrder(OrderRequestDto orderRequestDto) {
        Long userId = orderRequestDto.getUserId();
        Long addressId = orderRequestDto.getAddressId();
        User user = userRepository.findById(userId).orElse(null);

        ErrorDto errorDto = new ErrorDto();
        System.out.println(user);
        if( user == null ) {
            errorDto.setMessage("User not found");
            errorDto.setErrorCode(11L);
            return ResponseEntity.status(404).body(errorDto);
        }

        Address address = addressRepository.findById(addressId).orElse(null);

        System.out.println(address);

        if(!address.getUser().getId().equals(userId))
            return ResponseEntity.status(403).body("User not authorized");


        Order order = new Order();


        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemDto orderItemDto : orderRequestDto.getOrderItems()) {
            Product product = productRepository.findById(orderItemDto.getProductId()).orElse(null);

            if (product == null) {
                errorDto.setErrorCode(12L);
                errorDto.setMessage("Product not found with ID: " + orderItemDto.getProductId());
                return ResponseEntity.status(404).body(errorDto);
            }


            System.out.println(product);
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedAt(LocalDateTime.now());
            orderItem.setPrice(orderItemDto.getPrice());
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setProductOrder(product);
            orderItem.setOrder(order);
            orderItems.add(orderItem);

        }

        order.setOrderItems(orderItems);
        order.setUserOrder(user);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus("PENDING");
        order.setShippingAddress(address);
        System.out.println(order);


        Order newOrder= orderRepository.save(order);

        System.out.println(newOrder);

        return ResponseEntity.ok().body(orderRequestDto);
    }

    @Override
    public ResponseEntity<?> getOrderById(Long id) {
        Order order =  orderRepository.findById(id).orElse(null);
        ErrorDto errorDto = new ErrorDto();
        if(order == null) {
            errorDto.setErrorCode(12L);
            errorDto.setMessage("Order not found");
            return ResponseEntity.status(404).body(errorDto);
        }
        Long userId = order.getUserOrder().getId();
        Long addressId = order.getShippingAddress().getAddressId();
        String status = order.getOrderStatus();


        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(userId);
        orderRequestDto.setAddressId(addressId);
        orderRequestDto.setStatus(status);

        List<OrderItemDto> orderItemDtos = toOrderItemDto(order, orderRequestDto);
        orderRequestDto.setOrderItems(orderItemDtos);


        return ResponseEntity.ok().body(orderRequestDto);
    }

    @Override
    public ResponseEntity<?> updateOrder(Long id, OrderRequestDto orderDto) {
        Order order =  orderRepository.findById(id).orElse(null);
        ErrorDto errorDto = new ErrorDto();

        if(order == null) {
            errorDto.setErrorCode(12L);
            errorDto.setMessage("Order not found");
            return ResponseEntity.status(404).body(errorDto);
        }
        System.out.println(order.getShippingAddress());

        System.out.println(orderDto.getStatus());
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(order.getUserOrder().getId());
        orderRequestDto.setAddressId(order.getShippingAddress().getAddressId());
        orderRequestDto.setStatus(orderDto.getStatus());
        List<OrderItemDto> orderItemDtos = toOrderItemDto(order, orderRequestDto);
        orderRequestDto.setOrderItems(orderItemDtos);

        orderRepository.save(order);


        return ResponseEntity.ok().body(orderRequestDto);
    }

    @Override
    public ResponseEntity<?> getOrderTotal(Long id) {
        Order order =  orderRepository.findById(id).orElse(null);
        ErrorDto errorDto = new ErrorDto();

        if(order == null) {
            errorDto.setErrorCode(12L);
            errorDto.setMessage("Order not found");
            return ResponseEntity.status(404).body(errorDto);
        }
        Double total = (double) 0;
        for(OrderItem orderItem : order.getOrderItems()) {
            total += orderItem.getQuantity()*orderItem.getPrice();
        }

        OrderSummaryDto orderSummaryDto = new OrderSummaryDto();
        orderSummaryDto.setOrderId(id);
        orderSummaryDto.setCustomerId(order.getUserOrder().getId());
        orderSummaryDto.setTotalPrice(total);

        int totalQUantity = 0;
        for(OrderItem orderItem : order.getOrderItems()) {
            totalQUantity += orderItem.getQuantity();
        }
        orderSummaryDto.setTotalQuantity(totalQUantity);




        System.out.println(order.getUserOrder().getId());



        return ResponseEntity.ok().body(orderSummaryDto) ;
    }

    private List<OrderItemDto> toOrderItemDto(Order order, OrderRequestDto orderRequestDto) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();

        for(OrderItem orderItem : order.getOrderItems()) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setPrice(orderItem.getPrice());
            orderItemDto.setQuantity(orderItem.getQuantity());
            orderItemDto.setProductId(orderItem.getProductOrder().getProductId());
            orderItemDtos.add(orderItemDto);
        }
        return orderItemDtos;

    }

    private OrderRequestDto toDto(Order order) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setUserId(order.getUserOrder().getId());

        return orderRequestDto;
    }
}
