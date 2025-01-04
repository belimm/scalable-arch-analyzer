package com.scalableanalyzer.microservice.cart.repository;

import com.scalableanalyzer.microservice.cart.entity.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsUserByEmail(String username);

    User findUserByEmail(String email);
}
