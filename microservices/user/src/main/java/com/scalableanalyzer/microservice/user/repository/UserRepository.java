package com.scalableanalyzer.microservice.user.repository;

import com.scalableanalyzer.microservice.user.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsUserByEmail(String username);

    User findUserByEmail(String email);
}
