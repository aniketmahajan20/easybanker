package com.example.demo.easybanker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.easybanker.request.RegistrationRequest;
import com.example.demo.easybanker.service.RegistrationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    
    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<String> register(HttpServletRequest httpServletRequestequest, @RequestBody @Valid RegistrationRequest registrationRequest) {
        String currentUrl = httpServletRequestequest.getRequestURL().toString();
        return ResponseEntity.ok(registrationService.register(registrationRequest, currentUrl));
    }

    @GetMapping("/confirmation")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(registrationService.confirmToken(token));
    }
}
