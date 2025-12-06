package com.example.RideShare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RideResponse {

    private String id;
    private String userId; // Passenger
    private String driverId; // Can be null if not accepted yet
    private String pickupLocation;
    private String dropLocation;
    private LocalDateTime createdAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime completedAt;
    private String status; // "REQUESTED", "ACCEPTED", "COMPLETED", "CANCELLED"
}

