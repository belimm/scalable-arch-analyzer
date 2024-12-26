package com.scalableanalyzer.microservice.user.service;

import com.scalableanalyzer.microservice.user.dto.ErrorDto;
import com.scalableanalyzer.microservice.user.dto.SuccessDto;
import com.scalableanalyzer.microservice.user.dto.UserDto;
import com.scalableanalyzer.microservice.user.entity.User;
import com.scalableanalyzer.microservice.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getPassword() == null) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(8L);
            errorDto.setMessage("Email and password are required");
            return ResponseEntity.badRequest().body(errorDto);
        }

        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage("Email is already in use.");
            errorDto.setErrorCode(9L);
            return ResponseEntity.badRequest().body(errorDto);
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole("NORMAL");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User newUser = userRepository.save(user);
        return ResponseEntity.ok().body(toUserDto(newUser));
    }

    @Override
    public ResponseEntity<?> loginUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getPassword() == null) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(10L);
            errorDto.setMessage("Email and password are required");
            return ResponseEntity.badRequest().body(errorDto);
        }

        User user = userRepository.findUserByEmail(userDto.getEmail());
        if (user == null || !user.getPassword().equals(userDto.getPassword())) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(11L);
            errorDto.setMessage("Invalid credentials.");
            return ResponseEntity.status(401).body(errorDto);
        }

        return ResponseEntity
                .ok()
                .body(toUserDto(user));
    }

    @Override
    public ResponseEntity<?> updateUser(Long userId,UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(11L);
            errorDto.setMessage("User not found.");
            return ResponseEntity.status(404).body(errorDto);
        }

        User user = optionalUser.get();
        user.setFirstName(userDto.getFirstName() != null ? userDto.getFirstName() : user.getFirstName());
        user.setLastName(userDto.getLastName() != null ? userDto.getLastName() : user.getLastName());
        user.setEmail(userDto.getEmail() != null ? userDto.getEmail() : user.getEmail());
        user.setPassword(userDto.getPassword() != null ? userDto.getPassword() : user.getPassword());
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok().body(toUserDto(updatedUser));
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(11L);
            errorDto.setMessage("User not found.");
            return ResponseEntity.status(404).body(errorDto);
        }

        userRepository.deleteById(userId);
        SuccessDto successDto = new SuccessDto();
        successDto.setMessage("User successfully deleted.");
        return ResponseEntity.ok(successDto);
    }

    @Override
    public ResponseEntity<?> findUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(11L);
            errorDto.setMessage("User not found.");
            return ResponseEntity.status(404).body(errorDto);
        }

        User user = optionalUser.get();
        return ResponseEntity.ok(toUserDto(user));
    }

    private UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPassword(user.getPassword());


        return userDto;
    }
}
