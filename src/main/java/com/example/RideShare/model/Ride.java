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

    private String driverId;
    private String source;
    private String destination;

    private LocalDateTime departureTime;
    private int availableSeats;
    private double pricePerSeat;

    private String status; // "OPEN", "FULL", "CANCELLED", "COMPLETED"
}

