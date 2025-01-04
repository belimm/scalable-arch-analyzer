package com.scalableanalyzer.microservice.microbridge.service;

import com.scalableanalyzer.microservice.microbridge.dto.CartItemDto;
import com.scalableanalyzer.microservice.microbridge.dto.ErrorDto;
import com.scalableanalyzer.microservice.microbridge.dto.SuccessDto;
import com.scalableanalyzer.microservice.microbridge.dto.UserDto;
import com.scalableanalyzer.microservice.microbridge.util.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Service
public class CartItemService {
    private final RestTemplate restTemplate;

    public CartItemService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> addItem(@PathVariable("cartId") Long cartId, @RequestBody CartItemDto cartItem) {

        String uri = String.format("http://localhost:8085/microservice/cart/%s/item", cartId);

        System.out.println(uri);
        try {
            CartItemDto response = restTemplate.postForObject(uri,cartItem,CartItemDto.class);

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

    public ResponseEntity<?> deleteCartItemFromCart(@PathVariable("cartId") Long cartId, @PathVariable("cartItemId") Long cartItemId) {
        String uri = "http://localhost:8085/microservice/cart/" + cartId + "/" + cartItemId;

        try {
            // Send DELETE request
            restTemplate.delete(uri);

            SuccessDto successDto = new SuccessDto();
            successDto.setMessage("Cart Item with Id of " + cartItemId + " successfully deleted from cart with Id of" + cartId);
            // Return success response
            return ResponseEntity
                    .ok()
                    .body(successDto);
        } catch (Exception e) { // In case of an error
            System.out.println("An error occurred while deleting cartItem from cart: " + e.getMessage());

            // Use ErrorCodes enum for error handling
            ErrorCodes errorCode = ErrorCodes.CART_ITEM_NOT_FOUND;

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }
}
