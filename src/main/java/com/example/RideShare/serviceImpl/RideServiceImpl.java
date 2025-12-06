package com.example.RideShare.serviceImpl;

import com.example.RideShare.dto.request.CreateRideRequest;
import com.example.RideShare.dto.response.RideResponse;
import com.example.RideShare.exception.ResourceNotFoundException;
import com.example.RideShare.exception.ValidationException;
import com.example.RideShare.model.Ride;
import com.example.RideShare.model.User;
import com.example.RideShare.repository.RideRepository;
import com.example.RideShare.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private User currentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public RideResponse createRide(CreateRideRequest request) {
        User passenger = currentUser();

        // Passenger creates a ride request
        Ride ride = Ride.builder()
                .userId(passenger.getId())
                .pickupLocation(request.getPickupLocation())
                .dropLocation(request.getDropLocation())
                .createdAt(LocalDateTime.now())
                .status("REQUESTED")
                .build();

        rideRepository.save(ride);

        return toResponse(ride);
    }

    @Override
    public List<RideResponse> getPendingRideRequests() {
        // Drivers see all pending ride requests
        List<Ride> rides = rideRepository.findByStatus("REQUESTED");
        return rides.stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public RideResponse acceptRide(String rideId) {
        User driver = currentUser();
        
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!"REQUESTED".equals(ride.getStatus())) {
            throw new ValidationException("Ride is not available for acceptance");
        }

        ride.setDriverId(driver.getId());
        ride.setAcceptedAt(LocalDateTime.now());
        ride.setStatus("ACCEPTED");

        rideRepository.save(ride);

        return toResponse(ride);
    }

    @Override
    public RideResponse completeRide(String rideId) {
        User driver = currentUser();
        
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!driver.getId().equals(ride.getDriverId())) {
            throw new ValidationException("Only the assigned driver can complete this ride");
        }

        if (!"ACCEPTED".equals(ride.getStatus())) {
            throw new ValidationException("Ride must be accepted before completion");
        }

        ride.setCompletedAt(LocalDateTime.now());
        ride.setStatus("COMPLETED");

        rideRepository.save(ride);

        return toResponse(ride);
    }

    @Override
    public RideResponse cancelRide(String rideId) {
        User user = currentUser();
        
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        // Only passenger can cancel
        if (!user.getId().equals(ride.getUserId())) {
            throw new ValidationException("Only the passenger who requested can cancel this ride");
        }

        if ("COMPLETED".equals(ride.getStatus())) {
            throw new ValidationException("Cannot cancel a completed ride");
        }

        ride.setStatus("CANCELLED");
        rideRepository.save(ride);

        return toResponse(ride);
    }

    @Override
    public List<RideResponse> getMyRides() {
        User user = currentUser();
        
        List<Ride> rides;
        if ("DRIVER".equals(user.getRole())) {
            // Get rides where user is the driver
            rides = rideRepository.findByDriverId(user.getId());
        } else {
            // Get rides where user is the passenger
            rides = rideRepository.findByUserId(user.getId());
        }
        
        return rides.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private RideResponse toResponse(Ride ride) {
        return RideResponse.builder()
                .id(ride.getId())
                .userId(ride.getUserId())
                .driverId(ride.getDriverId())
                .pickupLocation(ride.getPickupLocation())
                .dropLocation(ride.getDropLocation())
                .createdAt(ride.getCreatedAt())
                .acceptedAt(ride.getAcceptedAt())
                .completedAt(ride.getCompletedAt())
                .status(ride.getStatus())
                .build();
    }
}

