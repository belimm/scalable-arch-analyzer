package com.scalableanalyzer.microservice.microbridge.service;

import com.scalableanalyzer.microservice.microbridge.dto.*;
import com.scalableanalyzer.microservice.microbridge.util.ErrorCodes;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        String uri = "http://localhost:8083/microservice/user";

        try {
            UserDto response = restTemplate.postForObject(uri,userDto,UserDto.class);

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


    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) {
        String uri = "http://localhost:8083/microservice/user/login";

        try {
            UserDto response = restTemplate.postForObject(uri,userDto,UserDto.class);

            return ResponseEntity
                    .ok()
                    .body(response);
        }
        catch (Exception er){ //In case of empty fields
            ErrorCodes errorCode = ErrorCodes.USER_NOT_FOUND;


            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());
            return ResponseEntity
                    .ok()
                    .body(errorDto);
        }
    }



    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        String uri = "http://localhost:8083/microservice/user/" + userId;

        try {
            // Create HTTP headers and request entity
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserDto> requestEntity = new HttpEntity<>(userDto, headers);

            // Send PUT request
            ResponseEntity<UserDto> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.PUT,
                    requestEntity,
                    UserDto.class
            );

            // Return response body
            return ResponseEntity
                    .ok()
                    .body(responseEntity.getBody());
        } catch (Exception e) { // In case of an error
            System.out.println("An error occurred while updating user: " + e.getMessage());

            // Use ErrorCodes enum for error handling
            ErrorCodes errorCode = ErrorCodes.USER_NOT_FOUND;

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }


    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        String uri = "http://localhost:8083/microservice/user/" + userId;

        try {
            // Send DELETE request
            restTemplate.delete(uri);

            SuccessDto successDto = new SuccessDto();
            successDto.setMessage("User with ID " + userId + " successfully deleted.");
            // Return success response
            return ResponseEntity
                    .ok()
                    .body(successDto);
        } catch (Exception e) { // In case of an error
            System.out.println("An error occurred while deleting user: " + e.getMessage());

            // Use ErrorCodes enum for error handling
            ErrorCodes errorCode = ErrorCodes.USER_NOT_FOUND;

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }


    public ResponseEntity<?> findUserById(@PathVariable Long userId) {
        String uri = "http://localhost:8083/microservice/user/"+userId;

        System.out.println(uri);

        try {
            UserDto user = restTemplate.getForObject(uri, UserDto.class);

            return ResponseEntity
                    .ok()
                    .body(user);
        } catch (Exception e) {
            System.out.println("An error occurred while fetching user: " + e.getMessage());
            ErrorCodes errorCode = ErrorCodes.USER_NOT_FOUND; // Replace with a more appropriate error code

            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(errorCode.getMessage());
            errorDto.setErrorCode(errorCode.getErrorCode());


            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(errorDto);
        }
    }

}
