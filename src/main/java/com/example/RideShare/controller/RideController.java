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
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping
    public ResponseEntity<RideResponse> create(@Valid @RequestBody CreateRideRequest request) {
        return ResponseEntity.ok(rideService.createRide(request));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RideResponse>> search(
            @RequestParam String source,
            @RequestParam String destination
    ) {
        return ResponseEntity.ok(rideService.searchRides(source, destination));
    }
}

