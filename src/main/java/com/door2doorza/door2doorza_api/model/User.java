package com.door2doorza.door2doorza_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role; // ADMIN, DRIVER, PASSENGER
    
    @Column
    private String phoneNumber;
    
    @Column
    private String profilePicture;
    
    // Driver-specific fields
    @Column
    private String driverLicenseNumber;
    
    @Column
    private LocalDateTime driverLicenseExpiry;
    
    @Column
    private Boolean driverVerified;
    
    @Column
    private Boolean driverAvailable;
    
    @Column
    private Double currentLatitude;
    
    @Column
    private Double currentLongitude;
    
    // Passenger-specific fields
    @Column
    private String preferredPaymentMethod;
    
    @Column
    private String emergencyContact;
    
    // Admin-specific fields
    @Column
    private String adminLevel; // SUPER_ADMIN, SUPPORT_ADMIN, etc.
    
    @Column
    private String department;
    
    // Common fields
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column
    private LocalDateTime updatedAt;
    
    // Default constructor
    public User() {
        this.createdAt = LocalDateTime.now();
        this.driverVerified = false;
        this.driverAvailable = false;
    }
    
    // Existing getters and setters...
    
    // New getters and setters for the added fields
    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }
    
    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }
    
    public LocalDateTime getDriverLicenseExpiry() {
        return driverLicenseExpiry;
    }
    
    public void setDriverLicenseExpiry(LocalDateTime driverLicenseExpiry) {
        this.driverLicenseExpiry = driverLicenseExpiry;
    }
    
    public Boolean getDriverVerified() {
        return driverVerified;
    }
    
    public void setDriverVerified(Boolean driverVerified) {
        this.driverVerified = driverVerified;
    }
    
    public Boolean getDriverAvailable() {
        return driverAvailable;
    }
    
    public void setDriverAvailable(Boolean driverAvailable) {
        this.driverAvailable = driverAvailable;
    }
    
    public Double getCurrentLatitude() {
        return currentLatitude;
    }
    
    public void setCurrentLatitude(Double currentLatitude) {
        this.currentLatitude = currentLatitude;
    }
    
    public Double getCurrentLongitude() {
        return currentLongitude;
    }
    
    public void setCurrentLongitude(Double currentLongitude) {
        this.currentLongitude = currentLongitude;
    }
    
    public String getPreferredPaymentMethod() {
        return preferredPaymentMethod;
    }
    
    public void setPreferredPaymentMethod(String preferredPaymentMethod) {
        this.preferredPaymentMethod = preferredPaymentMethod;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public String getAdminLevel() {
        return adminLevel;
    }
    
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    // Keep all existing getters and setters for the original fields
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getProfilePicture() {
        return profilePicture;
    }
    
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}