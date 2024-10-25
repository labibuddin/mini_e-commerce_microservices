package org.user.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.user.userservice.entity.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findFirstByToken(String token);
}