package org.example.onlineshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.onlineshop.model.User;
import org.example.onlineshop.repository.UserRepository;
import org.example.onlineshop.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findOptionalByEmail(String email) {
        return userRepository.findOptionalByEmail(email);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findOptionalByEmail(email).orElse(null);
    }

    @Override
    public void deleteById(int id) {
      userRepository.deleteById(id);
    }
}
