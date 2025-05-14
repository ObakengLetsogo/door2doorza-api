package com.door2doorza.door2doorza_api.controller;

import com.door2doorza.door2doorza_api.model.Trip;
import com.door2doorza.door2doorza_api.model.User;
import com.door2doorza.door2doorza_api.model.Vehicle;
import com.door2doorza.door2doorza_api.service.TripService;
import com.door2doorza.door2doorza_api.service.UserService;
import com.door2doorza.door2doorza_api.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private TripService tripService;
    
    @Autowired
    private VehicleService vehicleService;
    
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(
            @PathVariable Long id, 
            @RequestBody Map<String, String> roleUpdate) {
        
        String role = roleUpdate.get("role");
        if (role == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return userService.updateUserRole(id, role)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Map<String, Boolean> response = new HashMap<>();
        if (userService.deleteUser(id)) {
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/trips")
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }
    
    @GetMapping("/trips/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return tripService.getTripById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/vehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
    
    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return vehicleService.getVehicleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/stats/users")
    public Map<String, Long> getUserStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalUsers", userService.countAllUsers());
        stats.put("drivers", userService.countUsersByRole("DRIVER"));
        stats.put("passengers", userService.countUsersByRole("PASSENGER"));
        stats.put("admins", userService.countUsersByRole("ADMIN"));
        return stats;
    }
    
    @GetMapping("/stats/trips")
    public Map<String, Long> getTripStats() {
        Map<String, Long> stats = new HashMap<>();
        stats.put("totalTrips", tripService.countAllTrips());
        stats.put("completed", tripService.countTripsByStatus("COMPLETED"));
        stats.put("inProgress", tripService.countTripsByStatus("IN_PROGRESS"));
        stats.put("cancelled", tripService.countTripsByStatus("CANCELLED"));
        return stats;
    }
}