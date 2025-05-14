package com.door2doorza.door2doorza_api.repository;

import com.door2doorza.door2doorza_api.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    
    List<Trip> findByPassengerId(Long passengerId);
    
    List<Trip> findByDriverId(Long driverId);
    
    List<Trip> findByStatus(String status);
    
    List<Trip> findByDriverIdAndStatus(Long driverId, String status);
    
    List<Trip> findByPassengerIdAndStatus(Long passengerId, String status);
}