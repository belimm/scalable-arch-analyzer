package com.scalableanalyzer.microservice.microbridge.service;

import com.scalableanalyzer.microservice.microbridge.dto.AddressDto;
import com.scalableanalyzer.microservice.microbridge.dto.CategoryDto;
import com.scalableanalyzer.microservice.microbridge.dto.ErrorDto;
import com.scalableanalyzer.microservice.microbridge.dto.UserDto;
import com.scalableanalyzer.microservice.microbridge.util.ErrorCodes;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AddressService {
    private final RestTemplate restTemplate;

    public AddressService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public ResponseEntity<?> getAddressById(@PathVariable("addressId") Long addressId) {
        String uri = "http://localhost:8083/microservice/address/"+addressId;

        System.out.println(uri);

        try {
            AddressDto response = restTemplate.getForObject(uri, AddressDto.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching address: " + e.getMessage());
            ErrorCodes errorCode = ErrorCodes.ADDRESS_NOT_FOUND; // Replace with a more appropriate error code

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());


            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }


    public ResponseEntity<?> getUserAddressByUserId(@PathVariable("userId") Long userId) {
        String uri = "http://localhost:8083/microservice/address/user/"+userId;

        System.out.println(uri);

        try {
            List<AddressDto> response = restTemplate.getForObject(uri, List.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching address: " + e.getMessage());
            ErrorCodes errorCode = ErrorCodes.ADDRESS_NOT_FOUND; // Replace with a more appropriate error code

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());


            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }


    public ResponseEntity<?> addAddress(@PathVariable("userId") Long userId, @RequestBody AddressDto addressDto) {

        String uri = "http://localhost:8083/microservice/address/"+userId;
        System.out.println("postttt man");
        System.out.println(uri);

        System.out.println(addressDto);
        try {
            AddressDto response = restTemplate.postForObject(uri,addressDto, AddressDto.class);

            System.out.println(response);

            return ResponseEntity
                    .ok()
                    .body(response);
        }
        catch (Exception er){ //In case of empty fields
            ErrorCodes errorCode = ErrorCodes.SERVER_ERROR;

            System.out.println(er);


            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());
            return ResponseEntity
                    .ok()
                    .body(errorDto);
        }
    }



    public ResponseEntity<?> updateAddress(@PathVariable("userId") Long userId, @RequestBody AddressDto addressDto) {
        String uri = "http://localhost:8083/microservice/address/" + userId;

        try {
            // Create HTTP headers and request entity
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AddressDto> requestEntity = new HttpEntity<>(addressDto, headers);

            // Send PUT request
            ResponseEntity<AddressDto> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    requestEntity,
                    AddressDto.class
            );

            // Return response body
            return ResponseEntity
                    .ok()
                    .body(responseEntity.getBody());
        } catch (Exception e) { // In case of an error
            System.out.println("An error occurred while updating user address: " + e.getMessage());

            // Use ErrorCodes enum for error handling
            ErrorCodes errorCode = ErrorCodes.ADDRESS_NOT_FOUND;

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }
}
