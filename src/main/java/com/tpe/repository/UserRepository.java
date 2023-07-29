package com.tpe.repository;

import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserName(String UserName) throws ResourceNotFoundException;
    // we will use UserRepository from UserDetailsService(Security) class, so we are handling exception here

    boolean existsByUsername(String username);

}
