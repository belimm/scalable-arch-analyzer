package com.scalableanalyzer.microservice.user.repository;

import com.scalableanalyzer.microservice.user.entity.Address;
import com.scalableanalyzer.microservice.user.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {
    List<Address> findByUser(User user);

}
