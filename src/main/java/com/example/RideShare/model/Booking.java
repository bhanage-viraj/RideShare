package com.example.RideShare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "bookings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    private String id;

    private String rideId;
    private String userId;
    private int seatsBooked;
    private double totalPrice;

    private LocalDateTime bookedAt;
    private String status; // "BOOKED", "CANCELLED"
}

