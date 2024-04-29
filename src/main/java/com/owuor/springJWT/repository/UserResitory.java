package com.owuor.springJWT.repository;

import com.owuor.springJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserResitory extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
}
