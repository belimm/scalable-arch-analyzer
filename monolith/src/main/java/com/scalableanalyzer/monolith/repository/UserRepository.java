package com.scalableanalyzer.monolith.repository;

import com.scalableanalyzer.monolith.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Boolean existsUserByEmail(String username);

    User findUserByEmail(String email);
}
