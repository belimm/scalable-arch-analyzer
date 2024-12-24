package com.scalableanalyzer.monolith.repository;

import com.scalableanalyzer.monolith.entity.Address;
import com.scalableanalyzer.monolith.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Long> {

    List<Address> findByUser(User user);
}
