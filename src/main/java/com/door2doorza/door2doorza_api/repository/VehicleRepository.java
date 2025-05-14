package com.door2doorza.door2doorza_api.repository;

import com.door2doorza.door2doorza_api.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    
    List<Vehicle> findByDriverId(Long driverId);
    
    List<Vehicle> findByDriverIdAndIsActive(Long driverId, Boolean isActive);
    
    Optional<Vehicle> findByLicensePlate(String licensePlate);
    
    List<Vehicle> findByVehicleType(String vehicleType);
    
    List<Vehicle> findByIsActive(Boolean isActive);
}