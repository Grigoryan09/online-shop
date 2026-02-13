package org.example.onlineshop.repository;

import org.example.onlineshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findOptionalByEmail(String email);
}
