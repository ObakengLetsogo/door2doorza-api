package com.door2doorza.door2doorza_api.controller;

import com.door2doorza.door2doorza_api.model.User;
import com.door2doorza.door2doorza_api.model.Vehicle;
import com.door2doorza.door2doorza_api.service.UserService;
import com.door2doorza.door2doorza_api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private VehicleService vehicleService;
    
    @GetMapping
    public List<User> getAllDrivers() {
        return userService.getUsersByRole("DRIVER");
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getDriverById(@PathVariable Long id) {
        return userService.getUserById(id)
                .filter(user -> "DRIVER".equals(user.getRole()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/available")
    public List<User> getAvailableDrivers() {
        return userService.getAvailableDrivers();
    }
    
    @GetMapping("/{id}/vehicles")
    public List<Vehicle> getDriverVehicles(@PathVariable Long id) {
        return vehicleService.getVehiclesByDriverId(id);
    }
    
    @PutMapping("/{id}/availability")
    public ResponseEntity<User> updateDriverAvailability(
            @PathVariable Long id, 
            @RequestBody Map<String, Boolean> availability) {
        
        Boolean isAvailable = availability.get("available");
        if (isAvailable == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return userService.updateDriverAvailability(id, isAvailable)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/location")
    public ResponseEntity<User> updateDriverLocation(
            @PathVariable Long id, 
            @RequestBody Map<String, Double> location) {
        
        Double latitude = location.get("latitude");
        Double longitude = location.get("longitude");
        
        if (latitude == null || longitude == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return userService.updateDriverLocation(id, latitude, longitude)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/verify")
    public ResponseEntity<User> verifyDriver(@PathVariable Long id) {
        return userService.verifyDriver(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}