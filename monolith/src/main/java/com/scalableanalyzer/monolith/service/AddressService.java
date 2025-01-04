package com.scalableanalyzer.monolith.service;

import com.scalableanalyzer.monolith.dto.AddressDto;
import com.scalableanalyzer.monolith.dto.UserDto;
import com.scalableanalyzer.monolith.entity.Address;
import com.scalableanalyzer.monolith.entity.User;
import com.scalableanalyzer.monolith.repository.AddressRepository;
import com.scalableanalyzer.monolith.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class
AddressService implements IAddressService{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getAllAddressesForUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }


        List<Address> addresses = addressRepository.findByUser(userOptional.get());

        System.out.println(addresses);

        List<AddressDto> addressDtos = getAddressDtos(addresses);


        return ResponseEntity.ok().body(addressDtos);

    }

    private static List<AddressDto> getAddressDtos(List<Address> addresses) {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address address : addresses) {
            AddressDto addressDto = new AddressDto();

            addressDto.setAddressId(address.getAddressId());
            addressDto.setCity(address.getCity());
            addressDto.setCountry(address.getCountry());
            addressDto.setState(address.getState());
            addressDto.setStreet(address.getStreet());
            addressDto.setPostalCode(address.getPostalCode());
            addressDto.setUpdatedAt(address.getUpdatedAt());
            addressDto.setCreatedAt(address.getCreatedAt());

            addressDtos.add(addressDto);
        }
        return addressDtos;
    }

    @Override
    public ResponseEntity<?> getAddressById(Long addressId) {
        Optional<Address> addressOptional = addressRepository.findById(addressId);
        if (addressOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Address not found");
        }

        return ResponseEntity.ok().body(toDto(addressOptional.get()));
    }

    @Override
    public ResponseEntity<?> addAddress(Long userId, AddressDto addressDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        Address address = new Address();

        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setPostalCode(addressDto.getPostalCode());
        address.setUser(userOptional.get());
        address.setCountry(addressDto.getCountry());
        address.setState(addressDto.getState());

        Address newAddress = addressRepository.save(address);
        return ResponseEntity.status(201).body(toDto(newAddress));
    }

    @Override
    public ResponseEntity<?> updateAddress(Long userId, AddressDto addressDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        Optional<Address> addressOptional = addressRepository.findById(addressDto.getAddressId());
        if (addressOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Address not found");
        }

        Address address = addressOptional.get();
        if (!address.getUser().getId().equals(userId)) {
            return ResponseEntity.status(403).body("Address does not belong to the user");
        }

        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setPostalCode(addressDto.getPostalCode());
        address.setUpdatedAt(LocalDateTime.now());

        Address newAddress  = addressRepository.save(address);
        return ResponseEntity.ok().body(toDto(newAddress));
    }


    private AddressDto toDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet(address.getStreet());
        addressDto.setCity(address.getCity());
        addressDto.setPostalCode(address.getPostalCode());
        addressDto.setState(address.getState());
        addressDto.setUpdatedAt(address.getUpdatedAt());
        addressDto.setCreatedAt(address.getCreatedAt());
        addressDto.setCountry(address.getCountry());
        addressDto.setAddressId(address.getAddressId());
        return addressDto;
    }
}
