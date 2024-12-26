package com.scalableanalyzer.microservice.user.service;

import com.scalableanalyzer.microservice.user.dto.AddressDto;
import com.scalableanalyzer.microservice.user.dto.ErrorDto;
import com.scalableanalyzer.microservice.user.entity.Address;
import com.scalableanalyzer.microservice.user.entity.User;
import com.scalableanalyzer.microservice.user.repository.AddressRepository;
import com.scalableanalyzer.microservice.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {
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
            ErrorDto errorDto = new ErrorDto();
            errorDto.setErrorCode(11L);
            errorDto.setMessage("User not found.");
            return ResponseEntity.status(404).body(errorDto);
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
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage("Address not found");
            errorDto.setErrorCode(5L);
            return ResponseEntity.status(404).body(errorDto);
        }

        return ResponseEntity.ok().body(toDto(addressOptional.get()));
    }

    @Override
    public ResponseEntity<?> addAddress(Long userId, AddressDto addressDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage("User not found");
            errorDto.setErrorCode(11L);
            return ResponseEntity.status(404).body(errorDto);
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
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage("User not found");
            errorDto.setErrorCode(11L);
            return ResponseEntity.status(404).body(errorDto);
        }

        Optional<Address> addressOptional = addressRepository.findById(addressDto.getAddressId());
        if (addressOptional.isEmpty()) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage("Address not found");
            errorDto.setErrorCode(5L);
            return ResponseEntity.status(404).body(errorDto);
        }

        Address address = addressOptional.get();
        if (!address.getUser().getId().equals(userId)) {
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage("Address does not belong to the user");
            errorDto.setErrorCode(7L);
            return ResponseEntity.status(403).body(errorDto);
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
