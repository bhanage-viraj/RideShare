package com.example.RideShare.serviceImpl;

import com.example.RideShare.dto.request.BookingRequest;
import com.example.RideShare.dto.response.BookingResponse;
import com.example.RideShare.exception.ResourceNotFoundException;
import com.example.RideShare.exception.ValidationException;
import com.example.RideShare.model.Booking;
import com.example.RideShare.model.Ride;
import com.example.RideShare.model.User;
import com.example.RideShare.repository.BookingRepository;
import com.example.RideShare.repository.RideRepository;
import com.example.RideShare.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RideRepository rideRepository;

    private User currentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        User user = currentUser();

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!"OPEN".equals(ride.getStatus())) {
            throw new ValidationException("Ride not open for booking");
        }

        if (ride.getAvailableSeats() < request.getSeats()) {
            throw new ValidationException("Not enough seats available");
        }

        ride.setAvailableSeats(ride.getAvailableSeats() - request.getSeats());
        if (ride.getAvailableSeats() == 0) {
            ride.setStatus("FULL");
        }
        rideRepository.save(ride);

        double totalPrice = ride.getPricePerSeat() * request.getSeats();

        Booking booking = Booking.builder()
                .rideId(ride.getId())
                .userId(user.getId())
                .seatsBooked(request.getSeats())
                .totalPrice(totalPrice)
                .bookedAt(LocalDateTime.now())
                .status("BOOKED")
                .build();

        bookingRepository.save(booking);

        return toResponse(booking);
    }

    @Override
    public List<BookingResponse> myBookings() {
        User user = currentUser();
        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .rideId(booking.getRideId())
                .userId(booking.getUserId())
                .seatsBooked(booking.getSeatsBooked())
                .totalPrice(booking.getTotalPrice())
                .bookedAt(booking.getBookedAt())
                .status(booking.getStatus())
                .build();
    }
}

