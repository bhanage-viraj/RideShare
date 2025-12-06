package com.example.RideShare.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRideRequest {

    @NotBlank
    private String pickupLocation;

    @NotBlank
    private String dropLocation;
}

