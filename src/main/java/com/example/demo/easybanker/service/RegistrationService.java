package com.example.demo.easybanker.service;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;

import com.example.demo.easybanker.entity.Token;
import com.example.demo.easybanker.entity.User;
import com.example.demo.easybanker.request.RegistrationRequest;

import jakarta.transaction.Transactional;


@Service
public class RegistrationService {
    private final UserService userService;

    private final TokenService tokenService;
    
    public RegistrationService(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService  = tokenService;
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
        String confirmationLink = "http://localhost:8080/registration/confirmation?token=" + token;
        // emailSender.send(request.getEmail(), buildEmail(request.getFirstname(), confirmationLink));
        return confirmationLink;
    }

    @Transactional
    public String confirmToken(String token) {
        String loginLink = "http://localhost:8080/registration/login";
        Token confirmationToken = tokenService
                    .getToken(token)
                    .orElseThrow(() -> new IllegalStateException("Token not found"));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException(buildConfirmedPage(loginLink, "Email already confirmed"));
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }
        tokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUser().getEmail());
        return buildConfirmedPage(loginLink,"Email Confirmed");
    }

    private String buildConfirmedPage(String link, String message){
        return "<p> " + message + ". Please click on the below link to Login: </p>" +
                "<blockquote><p> <a href=\"" + link + "\">Login</a> </p>";
    }
}
