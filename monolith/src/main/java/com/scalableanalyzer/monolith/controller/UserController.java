package com.scalableanalyzer.monolith.controller;

import com.scalableanalyzer.monolith.dto.UserDto;
import com.scalableanalyzer.monolith.entity.User;
import com.scalableanalyzer.monolith.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monolith")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDto user) {
        return userService.createUser(user);
    }

    @GetMapping("/user")
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
