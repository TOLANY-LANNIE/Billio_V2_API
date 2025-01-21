package com.billio.billio_be.Repository;

import com.billio.billio_be.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(User.Role role);
    List<User> findByIsActive(Boolean isActive);
    boolean existsByEmail(String email);
    List<User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
}
