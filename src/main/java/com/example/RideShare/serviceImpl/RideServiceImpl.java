package com.example.RideShare.serviceImpl;

import com.example.RideShare.dto.request.CreateRideRequest;
import com.example.RideShare.dto.response.RideResponse;
import com.example.RideShare.model.Ride;
import com.example.RideShare.model.User;
import com.example.RideShare.repository.RideRepository;
import com.example.RideShare.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private User currentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public RideResponse createRide(CreateRideRequest request) {
        User driver = currentUser();

        Ride ride = Ride.builder()
                .driverId(driver.getId())
                .source(request.getSource())
                .destination(request.getDestination())
                .departureTime(request.getDepartureTime())
                .availableSeats(request.getAvailableSeats())
                .pricePerSeat(request.getPricePerSeat())
                .status("OPEN")
                .build();

        rideRepository.save(ride);

        return toResponse(ride);
    }

    @Override
    public List<RideResponse> searchRides(String source, String destination) {
        List<Ride> rides = rideRepository.findBySourceAndDestinationAndDepartureTimeAfter(
                source,
                destination,
                LocalDateTime.now()
        );
        return rides.stream().map(this::toResponse).toList();
    }

    private RideResponse toResponse(Ride ride) {
        return RideResponse.builder()
                .id(ride.getId())
                .driverId(ride.getDriverId())
                .source(ride.getSource())
                .destination(ride.getDestination())
                .departureTime(ride.getDepartureTime())
                .availableSeats(ride.getAvailableSeats())
                .pricePerSeat(ride.getPricePerSeat())
                .status(ride.getStatus())
                .build();
    }
}

