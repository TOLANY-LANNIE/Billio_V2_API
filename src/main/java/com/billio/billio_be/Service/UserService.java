package com.billio.billio_be.Service;

import com.billio.billio_be.Entity.User;
import com.billio.billio_be.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getActiveUsers() {
        return userRepository.findByIsActiveTrue();
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> updateUser(UUID id, User userDetails) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    if (userDetails.getEmail() != null &&
                            !userDetails.getEmail().equals(existingUser.getEmail()) &&
                            userRepository.existsByEmail(userDetails.getEmail())) {
                        throw new RuntimeException("Email already exists");
                    }

                    if (userDetails.getEmail() != null) {
                        existingUser.setEmail(userDetails.getEmail());
                    }
                    if (userDetails.getPasswordHash() != null) {
                        existingUser.setPasswordHash(userDetails.getPasswordHash());
                    }
                    if (userDetails.getFirstName() != null) {
                        existingUser.setFirstName(userDetails.getFirstName());
                    }
                    if (userDetails.getLastName() != null) {
                        existingUser.setLastName(userDetails.getLastName());
                    }
                    if (userDetails.getRole() != null) {
                        existingUser.setRole(userDetails.getRole());
                    }
                    if (userDetails.getIsActive() != null) {
                        existingUser.setIsActive(userDetails.getIsActive());
                    }
                    return userRepository.save(existingUser);
                });
    }

    public boolean deleteUser(UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    public boolean deactivateUser(UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setIsActive(false);
                    userRepository.save(user);
                    return true;
                })
                .orElse(false);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash())); // Encrypt password
        return userRepository.save(user);
    }

}
