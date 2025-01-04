package com.scalableanalyzer.microservice.user.controller;

import com.scalableanalyzer.microservice.user.dto.UserDto;
import com.scalableanalyzer.microservice.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservice")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto user) {
        return userService.loginUser(user);
    }


    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserDto user) {
        return userService.updateUser(userId, user);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }
}
