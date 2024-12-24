package com.scalableanalyzer.monolith.service;

import com.scalableanalyzer.monolith.dto.UserDto;
import com.scalableanalyzer.monolith.entity.User;
import com.scalableanalyzer.monolith.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and Password are required.");
        }

        if (userRepository.existsUserByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email is already in use.");
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
            return ResponseEntity.badRequest().body("Email and Password are required.");
        }

        User user = userRepository.findUserByEmail(userDto.getEmail());
        if (user == null || !user.getPassword().equals(userDto.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials.");
        }

        return ResponseEntity
                .ok()
                .body(toUserDto(user));
    }

    @Override
    public ResponseEntity<?> updateUser(Long userId,UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
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
            return ResponseEntity.status(404).body("User not found.");
        }

        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @Override
    public ResponseEntity<?> findUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
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
