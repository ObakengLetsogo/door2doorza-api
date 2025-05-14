package com.door2doorza.door2doorza_api.service;

import com.door2doorza.door2doorza_api.model.Trip;
import com.door2doorza.door2doorza_api.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TripService {
    
    private final TripRepository tripRepository;
    
    @Autowired
    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }
    
    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }
    
    public Optional<Trip> getTripById(Long id) {
        return tripRepository.findById(id);
    }
    
    public List<Trip> getTripsByPassengerId(Long passengerId) {
        return tripRepository.findByPassengerId(passengerId);
    }
    
    public List<Trip> getTripsByDriverId(Long driverId) {
        return tripRepository.findByDriverId(driverId);
    }
    
    public List<Trip> getTripsByStatus(String status) {
        return tripRepository.findByStatus(status);
    }
    
    public List<Trip> getAvailableTrips() {
        return tripRepository.findByStatus("REQUESTED");
    }
    
    public Trip createTrip(Trip trip) {
        trip.setCreatedAt(LocalDateTime.now());
        trip.setStatus("REQUESTED");
        return tripRepository.save(trip);
    }
    
    public Optional<Trip> assignDriver(Long tripId, Long driverId) {
        return tripRepository.findById(tripId).map(trip -> {
            trip.setDriverId(driverId);
            trip.setStatus("ASSIGNED");
            trip.setUpdatedAt(LocalDateTime.now());
            return tripRepository.save(trip);
        });
    }
    
    public Optional<Trip> updateTripStatus(Long tripId, String status) {
        return tripRepository.findById(tripId).map(trip -> {
            trip.setStatus(status);
            trip.setUpdatedAt(LocalDateTime.now());
            return tripRepository.save(trip);
        });
    }
    
    public Optional<Trip> updateTrip(Long id, Trip tripDetails) {
        return tripRepository.findById(id).map(existingTrip -> {
            if (tripDetails.getPickupLocation() != null) {
                existingTrip.setPickupLocation(tripDetails.getPickupLocation());
            }
            if (tripDetails.getDropoffLocation() != null) {
                existingTrip.setDropoffLocation(tripDetails.getDropoffLocation());
            }
            if (tripDetails.getScheduledTime() != null) {
                existingTrip.setScheduledTime(tripDetails.getScheduledTime());
            }
            if (tripDetails.getFare() != null) {
                existingTrip.setFare(tripDetails.getFare());
            }
            if (tripDetails.getPickupLatitude() != null) {
                existingTrip.setPickupLatitude(tripDetails.getPickupLatitude());
            }
            if (tripDetails.getPickupLongitude() != null) {
                existingTrip.setPickupLongitude(tripDetails.getPickupLongitude());
            }
            if (tripDetails.getDropoffLatitude() != null) {
                existingTrip.setDropoffLatitude(tripDetails.getDropoffLatitude());
            }
            if (tripDetails.getDropoffLongitude() != null) {
                existingTrip.setDropoffLongitude(tripDetails.getDropoffLongitude());
            }
            
            existingTrip.setUpdatedAt(LocalDateTime.now());
            return tripRepository.save(existingTrip);
        });
    }
    
    public boolean deleteTrip(Long id) {
        if (tripRepository.existsById(id)) {
            tripRepository.deleteById(id);
            return true;
        }
        return false;
    }
}