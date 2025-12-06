package com.example.RideShare.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingResponse {

    private String id;
    private String rideId;
    private String userId;
    private int seatsBooked;
    private double totalPrice;
    private LocalDateTime bookedAt;
    private String status;
}

