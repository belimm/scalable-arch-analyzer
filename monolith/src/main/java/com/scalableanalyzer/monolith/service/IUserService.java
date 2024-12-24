package com.scalableanalyzer.monolith.service;

import com.scalableanalyzer.monolith.dto.UserDto;
import com.scalableanalyzer.monolith.entity.User;
import org.springframework.http.ResponseEntity;

public interface IUserService  {
    public ResponseEntity<?> createUser(UserDto user);

    public ResponseEntity<?> loginUser(UserDto user);

    public ResponseEntity<?> updateUser(Long userId, UserDto user);

    public ResponseEntity<?> deleteUser(Long userId);

    public ResponseEntity<?> findUserById(Long userId);
}
