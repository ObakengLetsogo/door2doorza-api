package com.door2doorza.door2doorza_api.service;

import com.door2doorza.door2doorza_api.model.User;
import com.door2doorza.door2doorza_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
    
    public List<User> getAvailableDrivers() {
        return userRepository.findByRoleAndDriverAvailable("DRIVER", true);
    }
    
    public User createUser(User user) {
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        
        // Set default values based on role
        if ("DRIVER".equals(user.getRole())) {
            user.setDriverVerified(false);
            user.setDriverAvailable(false);
        }
        
        return userRepository.save(user);
    }
    
    public Optional<User> updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(existingUser -> {
            if (userDetails.getFirstName() != null) {
                existingUser.setFirstName(userDetails.getFirstName());
            }
            if (userDetails.getLastName() != null) {
                existingUser.setLastName(userDetails.getLastName());
            }
            if (userDetails.getPhoneNumber() != null) {
                existingUser.setPhoneNumber(userDetails.getPhoneNumber());
            }
            if (userDetails.getProfilePicture() != null) {
                existingUser.setProfilePicture(userDetails.getProfilePicture());
            }
            
            // Update role-specific fields
            if ("DRIVER".equals(existingUser.getRole())) {
                if (userDetails.getDriverLicenseNumber() != null) {
                    existingUser.setDriverLicenseNumber(userDetails.getDriverLicenseNumber());
                }
                if (userDetails.getDriverLicenseExpiry() != null) {
                    existingUser.setDriverLicenseExpiry(userDetails.getDriverLicenseExpiry());
                }
            } else if ("PASSENGER".equals(existingUser.getRole())) {
                if (userDetails.getPreferredPaymentMethod() != null) {
                    existingUser.setPreferredPaymentMethod(userDetails.getPreferredPaymentMethod());
                }
                if (userDetails.getEmergencyContact() != null) {
                    existingUser.setEmergencyContact(userDetails.getEmergencyContact());
                }
            } else if ("ADMIN".equals(existingUser.getRole())) {
                if (userDetails.getAdminLevel() != null) {
                    existingUser.setAdminLevel(userDetails.getAdminLevel());
                }
                if (userDetails.getDepartment() != null) {
                    existingUser.setDepartment(userDetails.getDepartment());
                }
            }
            
            // If password is being updated, encrypt it
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            
            existingUser.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(existingUser);
        });
    }
    
    public Optional<User> updateDriverAvailability(Long id, Boolean available) {
        return userRepository.findById(id)
                .filter(user -> "DRIVER".equals(user.getRole()))
                .map(driver -> {
                    driver.setDriverAvailable(available);
                    driver.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(driver);
                });
    }
    
    public Optional<User> updateDriverLocation(Long id, Double latitude, Double longitude) {
        return userRepository.findById(id)
                .filter(user -> "DRIVER".equals(user.getRole()))
                .map(driver -> {
                    driver.setCurrentLatitude(latitude);
                    driver.setCurrentLongitude(longitude);
                    driver.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(driver);
                });
    }
    
    public Optional<User> verifyDriver(Long id) {
        return userRepository.findById(id)
                .filter(user -> "DRIVER".equals(user.getRole()))
                .map(driver -> {
                    driver.setDriverVerified(true);
                    driver.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(driver);
                });
    }
    
    public Optional<User> updatePassengerPaymentMethod(Long id, String method) {
        return userRepository.findById(id)
                .filter(user -> "PASSENGER".equals(user.getRole()))
                .map(passenger -> {
                    passenger.setPreferredPaymentMethod(method);
                    passenger.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(passenger);
                });
    }
    
    public Optional<User> updatePassengerEmergencyContact(Long id, String contact) {
        return userRepository.findById(id)
                .filter(user -> "PASSENGER".equals(user.getRole()))
                .map(passenger -> {
                    passenger.setEmergencyContact(contact);
                    passenger.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(passenger);
                });
    }
    
    public Optional<User> updateUserRole(Long id, String role) {
        return userRepository.findById(id).map(user -> {
            user.setRole(role);
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        });
    }
    
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    public boolean hasRole(Long userId, String role) {
        return userRepository.findById(userId)
                .map(user -> user.getRole().equals(role))
                .orElse(false);
    }
    
    public long countAllUsers() {
        return userRepository.count();
    }
    
    public long countUsersByRole(String role) {
        return userRepository.countByRole(role);
    }
}