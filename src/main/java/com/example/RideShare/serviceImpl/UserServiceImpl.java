package com.example.RideShare.serviceImpl;

import com.example.RideShare.dto.response.UserResponse;
import com.example.RideShare.exception.ResourceNotFoundException;
import com.example.RideShare.model.User;
import com.example.RideShare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private User getCurrentUserEntity() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            return user;
        }
        throw new ResourceNotFoundException("User not found in context");
    }

    @Override
    public UserResponse getCurrentUser() {
        User user = getCurrentUserEntity();
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }
}

