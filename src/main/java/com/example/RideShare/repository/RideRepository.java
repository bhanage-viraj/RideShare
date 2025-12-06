package com.example.RideShare.repository;

import com.example.RideShare.model.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findBySourceAndDestinationAndDepartureTimeAfter(
            String source,
            String destination,
            LocalDateTime departureTime
    );
}

