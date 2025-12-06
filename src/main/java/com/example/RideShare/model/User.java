package com.example.RideShare.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String username;
    private String name;
    private String email;
    private String password;

    private String phone;
    private String role; // "PASSENGER" or "DRIVER"
    private Set<String> roles;
}

