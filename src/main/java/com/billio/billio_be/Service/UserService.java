package com.billio.billio_be.Service;

import com.billio.billio_be.DTO.UserDTO;
import com.billio.billio_be.Entity.User;
import com.billio.billio_be.Repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new user with password encryption
    // Register a new user
    public User register(User user) {
        // Check if the email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Encrypt the password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPasswordHash(encoder.encode(user.getPasswordHash()));

        user.setIsActive(true); // Set user as active

        return userRepository.save(user);
    }

    // Get all users and convert them to DTOs
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a user by ID and convert to DTO
    public Optional<UserDTO> getUserById(UUID id) {
        return userRepository.findById(id)
                .map(this::convertToDTO);
    }

    // Save a new user (used for scenarios where password encryption is not needed)
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    // Update user (returns updated DTO)
    public Optional<UserDTO> updateUser(UUID id, User updatedUser) {
        return userRepository.findById(id).map(existingUser -> {
            // Update fields
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setRole(updatedUser.getRole());
            existingUser.setIsActive(updatedUser.getIsActive());

            // Save updated user (updatedAt is handled by @PreUpdate in the entity)
            User savedUser = userRepository.save(existingUser);
            return convertToDTO(savedUser);
        });
    }

    // Delete a user
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    // Convert User entity to UserDTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole(),
                user.getIsActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
