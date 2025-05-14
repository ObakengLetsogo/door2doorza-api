package com.door2doorza.door2doorza_api.controller;

import com.door2doorza.door2doorza_api.model.Trip;
import com.door2doorza.door2doorza_api.model.User;
import com.door2doorza.door2doorza_api.service.TripService;
import com.door2doorza.door2doorza_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin(origins = "*")
public class PassengerController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private TripService tripService;
    
    @GetMapping
    public List<User> getAllPassengers() {
        return userService.getUsersByRole("PASSENGER");
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getPassengerById(@PathVariable Long id) {
        return userService.getUserById(id)
                .filter(user -> "PASSENGER".equals(user.getRole()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/{id}/trips")
    public List<Trip> getPassengerTrips(@PathVariable Long id) {
        return tripService.getTripsByPassengerId(id);
    }
    
    @PutMapping("/{id}/payment-method")
    public ResponseEntity<User> updatePaymentMethod(
            @PathVariable Long id, 
            @RequestBody Map<String, String> paymentMethod) {
        
        String method = paymentMethod.get("method");
        if (method == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return userService.updatePassengerPaymentMethod(id, method)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/emergency-contact")
    public ResponseEntity<User> updateEmergencyContact(
            @PathVariable Long id, 
            @RequestBody Map<String, String> emergencyContact) {
        
        String contact = emergencyContact.get("contact");
        if (contact == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return userService.updatePassengerEmergencyContact(id, contact)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}