package com.example.RideShare.service;

import com.example.RideShare.dto.request.LoginRequest;
import com.example.RideShare.dto.request.RegisterRequest;
import com.example.RideShare.dto.response.JwtResponse;
import com.example.RideShare.dto.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    JwtResponse login(LoginRequest request);
}

