package com.door2doorza.door2doorza_api.service;

import com.door2doorza.door2doorza_api.model.Vehicle;
import com.door2doorza.door2doorza_api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }
    
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }
    
    public List<Vehicle> getVehiclesByDriverId(Long driverId) {
        return vehicleRepository.findByDriverId(driverId);
    }
    
    public List<Vehicle> getActiveVehiclesByDriverId(Long driverId) {
        return vehicleRepository.findByDriverIdAndIsActive(driverId, true);
    }
    
    public Optional<Vehicle> getVehicleByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }
    
    public List<Vehicle> getVehiclesByType(String vehicleType) {
        return vehicleRepository.findByVehicleType(vehicleType);
    }
    
    public List<Vehicle> getActiveVehicles() {
        return vehicleRepository.findByIsActive(true);
    }
    
    public Vehicle createVehicle(Vehicle vehicle) {
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setIsActive(true);
        return vehicleRepository.save(vehicle);
    }
    
    public Optional<Vehicle> updateVehicle(Long id, Vehicle vehicleDetails) {
        return vehicleRepository.findById(id).map(existingVehicle -> {
            if (vehicleDetails.getMake() != null) {
                existingVehicle.setMake(vehicleDetails.getMake());
            }
            if (vehicleDetails.getModel() != null) {
                existingVehicle.setModel(vehicleDetails.getModel());
            }
            if (vehicleDetails.getYear() != null) {
                existingVehicle.setYear(vehicleDetails.getYear());
            }
            if (vehicleDetails.getColor() != null) {
                existingVehicle.setColor(vehicleDetails.getColor());
            }
            if (vehicleDetails.getVehicleType() != null) {
                existingVehicle.setVehicleType(vehicleDetails.getVehicleType());
            }
            if (vehicleDetails.getIsActive() != null) {
                existingVehicle.setIsActive(vehicleDetails.getIsActive());
            }
            if (vehicleDetails.getPhotoUrl() != null) {
                existingVehicle.setPhotoUrl(vehicleDetails.getPhotoUrl());
            }
            
            existingVehicle.setUpdatedAt(LocalDateTime.now());
            return vehicleRepository.save(existingVehicle);
        });
    }
    
    public Optional<Vehicle> activateVehicle(Long id) {
        return vehicleRepository.findById(id).map(vehicle -> {
            vehicle.setIsActive(true);
            vehicle.setUpdatedAt(LocalDateTime.now());
            return vehicleRepository.save(vehicle);
        });
    }
    
    public Optional<Vehicle> deactivateVehicle(Long id) {
        return vehicleRepository.findById(id).map(vehicle -> {
            vehicle.setIsActive(false);
            vehicle.setUpdatedAt(LocalDateTime.now());
            return vehicleRepository.save(vehicle);
        });
    }
    
    public boolean deleteVehicle(Long id) {
        if (vehicleRepository.existsById(id)) {
            vehicleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}