package com.example.RideShare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RideResponse {

    private String id;
    private String driverId;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private int availableSeats;
    private double pricePerSeat;
    private String status;
}

