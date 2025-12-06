package com.example.RideShare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rides")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    private String id;

    private String userId; // Passenger who requested the ride
    private String driverId; // Driver who accepts the ride (nullable initially)
    
    private String pickupLocation;
    private String dropLocation;

    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime completedAt;

    private String status; // "REQUESTED", "ACCEPTED", "COMPLETED", "CANCELLED"
}

