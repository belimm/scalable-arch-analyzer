package com.scalableanalyzer.microservice.microbridge.service;

import com.scalableanalyzer.microservice.microbridge.dto.*;
import com.scalableanalyzer.microservice.microbridge.util.ErrorCodes;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class OrderService {
    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        String uri = String.format("http://localhost:8084/microservice/orders");

        System.out.println(uri);
        try {
            OrderRequestDto response = restTemplate.postForObject(uri,orderRequestDto,OrderRequestDto.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        }
        catch (Exception er){ //In case of empty fields
            ErrorCodes errorCode = ErrorCodes.SERVER_ERROR;


            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());
            return ResponseEntity
                    .ok()
                    .body(errorDto);
        }
    }


    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        String uri = "http://localhost:8084/microservice/orders/"+orderId;

        System.out.println(uri);

        try {
            OrderRequestDto response = restTemplate.getForObject(uri, OrderRequestDto.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching order: " + e.getMessage());
            ErrorCodes errorCode = ErrorCodes.ORDER_NOT_FOUND;

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());


            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }


    public ResponseEntity<?> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequestDto orderRequestDto) {
        String uri = String.format("http://localhost:8084/microservice/orders/%s/status", orderId);

        System.out.println(uri);

        try {
            // Create HTTP headers and request entity
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(orderRequestDto, headers);

            // Send PUT request
            ResponseEntity<OrderRequestDto> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    requestEntity,
                    OrderRequestDto.class
            );

            // Return response body
            return ResponseEntity
                    .ok()
                    .body(responseEntity.getBody());
        } catch (Exception e) { // In case of an error
            System.out.println("An error occurred while updating order status: " + e.getMessage());

            // Use ErrorCodes enum for error handling
            ErrorCodes errorCode = ErrorCodes.ORDER_NOT_FOUND;

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }


    public ResponseEntity<?> getOrderTotal(@PathVariable Long orderId) {
        String uri = String.format("http://localhost:8084/microservice/orders/%s/total", orderId);


        System.out.println(uri);

        try {
            OrderSummaryDto response = restTemplate.getForObject(uri, OrderSummaryDto.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching order: " + e.getMessage());
            ErrorCodes errorCode = ErrorCodes.ORDER_NOT_FOUND;

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());


            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }

}
