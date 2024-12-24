package com.scalableanalyzer.monolith.service;

import com.scalableanalyzer.monolith.dto.AddressDto;
import com.scalableanalyzer.monolith.dto.UserDto;
import com.scalableanalyzer.monolith.entity.Address;
import com.scalableanalyzer.monolith.entity.User;
import org.springframework.http.ResponseEntity;

public interface IAddressService {
    public ResponseEntity<?> getAllAddressesForUser(Long userId);
    public ResponseEntity<?> getAddressById(Long addressId);
    public ResponseEntity<?> addAddress(Long userId, AddressDto address);
    public ResponseEntity<?> updateAddress(Long userId, AddressDto address);
}
