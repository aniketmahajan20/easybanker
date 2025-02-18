package com.example.demo.easybanker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.easybanker.request.RegistrationRequest;
import com.example.demo.easybanker.service.RegistrationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    
    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request) {
        return ResponseEntity.ok(registrationService.register(request));
    }
}
