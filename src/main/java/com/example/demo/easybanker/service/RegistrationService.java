package com.example.demo.easybanker.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.demo.easybanker.entity.User;
import com.example.demo.easybanker.request.RegistrationRequest;


@Service
public class RegistrationService {
    private final UserService userService;
    
    public RegistrationService(UserService userService) {
        this.userService = userService;
    }

    public String register(RegistrationRequest request) {
        User user = new User(
            request.getFirstname(),
            request.getLastname(),
            request.getEmail(),
            request.getPassword(),
            List.of("ROLE_USER")
        );
        String token = userService.signUpUser(user);
        return token;
    }
}
