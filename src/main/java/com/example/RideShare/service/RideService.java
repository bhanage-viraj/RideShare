package com.example.RideShare.service;

import com.example.RideShare.dto.request.CreateRideRequest;
import com.example.RideShare.dto.response.RideResponse;

import java.util.List;

public interface RideService {
    RideResponse createRide(CreateRideRequest request); // Passenger creates ride request
    List<RideResponse> getPendingRideRequests(); // Driver views pending requests
    RideResponse acceptRide(String rideId); // Driver accepts a ride
    RideResponse completeRide(String rideId); // Driver completes a ride
    RideResponse cancelRide(String rideId); // Cancel a ride
    List<RideResponse> getMyRides(); // Get user's rides (passenger or driver)
}

