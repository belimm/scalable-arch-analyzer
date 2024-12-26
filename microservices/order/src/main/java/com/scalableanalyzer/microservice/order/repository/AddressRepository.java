package com.scalableanalyzer.microservice.order.repository;

import com.scalableanalyzer.microservice.order.entity.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
