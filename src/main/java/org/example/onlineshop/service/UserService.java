package org.example.onlineshop.service;

import org.example.onlineshop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService  {

    void save(User user);

    List<User> findAll();

    Optional<User> findOptionalByEmail(String email);

    User getByEmail(String email);

    void deleteById(int id);

}
