package com.scalableanalyzer.microservice.user.service;

import com.scalableanalyzer.microservice.user.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    public ResponseEntity<?> createUser(UserDto user);

    public ResponseEntity<?> loginUser(UserDto user);

    public ResponseEntity<?> updateUser(Long userId, UserDto user);

    public ResponseEntity<?> deleteUser(Long userId);

    public ResponseEntity<?> findUserById(Long userId);
}
