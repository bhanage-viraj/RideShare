package com.example.RideShare.controller;

import com.example.RideShare.dto.request.CreateRideRequest;
import com.example.RideShare.dto.response.RideResponse;
import com.example.RideShare.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    // Passenger creates a ride request
    @PostMapping
    public ResponseEntity<RideResponse> createRide(@Valid @RequestBody CreateRideRequest request) {
        return ResponseEntity.ok(rideService.createRide(request));
    }

    // Get user's rides (passenger or driver based on role)
    @GetMapping("/my")
    public ResponseEntity<List<RideResponse>> getMyRides() {
        return ResponseEntity.ok(rideService.getMyRides());
    }

    // Cancel a ride (passenger only)
    @PostMapping("/{id}/cancel")
    public ResponseEntity<RideResponse> cancelRide(@PathVariable String id) {
        return ResponseEntity.ok(rideService.cancelRide(id));
    }

    // Driver views all pending ride requests
    @GetMapping("/requests")
    public ResponseEntity<List<RideResponse>> getPendingRequests() {
        return ResponseEntity.ok(rideService.getPendingRideRequests());
    }

    // Driver accepts a ride request
    @PostMapping("/{id}/accept")
    public ResponseEntity<RideResponse> acceptRide(@PathVariable String id) {
        return ResponseEntity.ok(rideService.acceptRide(id));
    }

    // Driver completes a ride
    @PostMapping("/{id}/complete")
    public ResponseEntity<RideResponse> completeRide(@PathVariable String id) {
        return ResponseEntity.ok(rideService.completeRide(id));
    }
}

