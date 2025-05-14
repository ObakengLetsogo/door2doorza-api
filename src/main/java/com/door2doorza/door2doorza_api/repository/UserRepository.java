package com.door2doorza.door2doorza_api.repository;

import com.door2doorza.door2doorza_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByRole(String role);
    
    List<User> findByRoleAndDriverAvailable(String role, Boolean available);
    
    long countByRole(String role);
}