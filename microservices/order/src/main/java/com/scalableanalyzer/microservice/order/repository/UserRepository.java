package com.scalableanalyzer.microservice.order.repository;

import com.scalableanalyzer.microservice.order.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
