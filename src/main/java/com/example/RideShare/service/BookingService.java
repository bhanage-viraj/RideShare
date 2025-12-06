package com.example.RideShare.service;

import com.example.RideShare.dto.request.BookingRequest;
import com.example.RideShare.dto.response.BookingResponse;

import java.util.List;

public interface BookingService {
    BookingResponse createBooking(BookingRequest request);
    List<BookingResponse> myBookings();
}

