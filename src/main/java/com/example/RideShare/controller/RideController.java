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
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    // Passenger creates a ride request
    @PostMapping("/rides")
    public ResponseEntity<RideResponse> createRide(@Valid @RequestBody CreateRideRequest request) {
        return ResponseEntity.ok(rideService.createRide(request));
    }

    // Cancel a ride (passenger only)
    @PostMapping("/rides/{id}/cancel")
    public ResponseEntity<RideResponse> cancelRide(@PathVariable String id) {
        return ResponseEntity.ok(rideService.cancelRide(id));
    }

    // Complete a ride (driver only)
    @PostMapping("/rides/{id}/complete")
    public ResponseEntity<RideResponse> completeRide(@PathVariable String id) {
        return ResponseEntity.ok(rideService.completeRide(id));
    }

    // Driver views all pending ride requests - EXACT PATH FROM SPEC
    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<RideResponse>> getPendingRequests() {
        return ResponseEntity.ok(rideService.getPendingRideRequests());
    }

    // Driver accepts a ride request - EXACT PATH FROM SPEC
    @PostMapping("/driver/rides/{id}/accept")
    public ResponseEntity<RideResponse> acceptRide(@PathVariable String id) {
        return ResponseEntity.ok(rideService.acceptRide(id));
    }

    // Get user's rides - EXACT PATH FROM SPEC
    @GetMapping("/user/rides")
    public ResponseEntity<List<RideResponse>> getMyRides() {
        return ResponseEntity.ok(rideService.getMyRides());
    }
}

