package com.billio.billio_be.Repository;

import com.billio.billio_be.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByIsActiveTrue();
    List<User> findByRole(User.Role role);
    Optional<User> findByEmailAndIsActiveTrue(String email);
}