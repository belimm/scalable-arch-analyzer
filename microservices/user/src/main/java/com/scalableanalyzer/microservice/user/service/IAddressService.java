package com.scalableanalyzer.microservice.user.service;

import org.springframework.http.ResponseEntity;
import com.scalableanalyzer.microservice.user.dto.AddressDto;

public interface IAddressService {
    ResponseEntity<?> getAddressById(Long id);
    ResponseEntity<?> getAllAddressesForUser(Long userId);

    ResponseEntity<?> addAddress(Long id, AddressDto addressDto);

    ResponseEntity<?> updateAddress(Long id, AddressDto addressDto);
}
