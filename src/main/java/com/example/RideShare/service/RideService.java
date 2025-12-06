package com.example.RideShare.service;

import com.example.RideShare.dto.request.CreateRideRequest;
import com.example.RideShare.dto.response.RideResponse;

import java.util.List;

public interface RideService {
    RideResponse createRide(CreateRideRequest request);
    List<RideResponse> searchRides(String source, String destination);
}

