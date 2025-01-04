package com.scalableanalyzer.microservice.microbridge.controller;

import com.scalableanalyzer.microservice.microbridge.dto.AddressDto;
import com.scalableanalyzer.microservice.microbridge.service.AddressService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/microservice/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable("addressId") Long addressId) {
        return addressService.getAddressById(addressId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserAddressByUserId(@PathVariable("userId") Long userId) {
        return addressService.getUserAddressByUserId(userId);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> addAddress(@PathVariable("userId") Long userId, @RequestBody AddressDto addressDto) {
        return addressService.addAddress(userId, addressDto);
    }


    @PutMapping("/{userId}")
    public ResponseEntity<?> updateAddress(@PathVariable("userId") Long userId, @RequestBody AddressDto addressDto) {
        return addressService.updateAddress(userId, addressDto);
    }





}
