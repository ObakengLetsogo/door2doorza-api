package com.door2doorza.door2doorza_api.controller;

import com.door2doorza.door2doorza_api.model.Trip;
import com.door2doorza.door2doorza_api.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/trips")
@CrossOrigin(origins = "*")
public class TripController {
    
    @Autowired
    private TripService tripService;
    
    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.getAllTrips();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripById(@PathVariable Long id) {
        return tripService.getTripById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/passenger/{passengerId}")
    public List<Trip> getTripsByPassengerId(@PathVariable Long passengerId) {
        return tripService.getTripsByPassengerId(passengerId);
    }
    
    @GetMapping("/driver/{driverId}")
    public List<Trip> getTripsByDriverId(@PathVariable Long driverId) {
        return tripService.getTripsByDriverId(driverId);
    }
    
    @GetMapping("/status/{status}")
    public List<Trip> getTripsByStatus(@PathVariable String status) {
        return tripService.getTripsByStatus(status);
    }
    
    @GetMapping("/available")
    public List<Trip> getAvailableTrips() {
        return tripService.getAvailableTrips();
    }
    
    @PostMapping
    public Trip createTrip(@RequestBody Trip trip) {
        return tripService.createTrip(trip);
    }
    
    @PutMapping("/{id}/assign/{driverId}")
    public ResponseEntity<Trip> assignDriver(@PathVariable Long id, @PathVariable Long driverId) {
        return tripService.assignDriver(id, driverId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Trip> updateTripStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return tripService.updateTripStatus(id, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Trip> updateTrip(@PathVariable Long id, @RequestBody Trip tripDetails) {
        return tripService.updateTrip(id, tripDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Long id) {
        Map<String, Boolean> response = new HashMap<>();
        if (tripService.deleteTrip(id)) {
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.notFound().build();
    }
}