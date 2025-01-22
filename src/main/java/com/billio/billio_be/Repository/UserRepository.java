package com.billio.billio_be.Repository;

import com.billio.billio_be.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    // Find a user by email
    Optional<User> findByEmail(String email);

    // Check if a user exists by email
    boolean existsByEmail(String email);

    // Find all active users
    List<User> findByIsActiveTrue();

    // Find users by role
    List<User> findByRole(String role);

    // Find an active user by email
    Optional<User> findByEmailAndIsActiveTrue(String email);
}
