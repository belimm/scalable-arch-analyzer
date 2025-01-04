package com.scalableanalyzer.microservice.microbridge.service;

import com.scalableanalyzer.microservice.microbridge.dto.*;
import com.scalableanalyzer.microservice.microbridge.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {
    private final RestTemplate restTemplate;

    public CartService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<?> createCart(@PathVariable("userId") Long userId) {
        String uri = "http://localhost:8085/microservice/cart/"+userId;

        System.out.println(uri);

        try {
            CartDto response = restTemplate.getForObject(uri, CartDto.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        } catch (Exception e) {
            System.out.println("An error occurred while creating cart: " + e.getMessage());
            ErrorCodes errorCode = ErrorCodes.SERVER_ERROR; // Replace with a more appropriate error code

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());


            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }

    }


    public ResponseEntity<?> deleteCart(@PathVariable("cartId") Long userId) {
        String uri = "http://localhost:8085/microservice/cart/" + userId;
        System.out.println("uri"+ uri);
        try {
            // Send DELETE request
            restTemplate.delete(uri);

            SuccessDto successDto = new SuccessDto();
            successDto.setMessage("Cart of User with ID " + userId + " successfully deleted.");
            // Return success response
            return ResponseEntity
                    .ok()
                    .body(successDto);
        } catch (Exception e) { // In case of an error
            System.out.println("An error occurred while deleting cart: " + e.getMessage());

            // Use ErrorCodes enum for error handling
            ErrorCodes errorCode = ErrorCodes.CART_NOT_FOUND;

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }

}
