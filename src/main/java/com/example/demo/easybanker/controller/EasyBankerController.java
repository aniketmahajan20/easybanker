package com.example.demo.easybanker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.easybanker.entity.AuthenticationRequest;
import com.example.demo.easybanker.entity.AuthenticationResponse;
import com.example.demo.easybanker.entity.User;
import com.example.demo.easybanker.service.UserService;
import com.example.demo.easybanker.util.JwtUtil;

@RestController
public class EasyBankerController {
    public final UserService userService;

    public EasyBankerController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome brother";
    }

    @GetMapping("/balance")
    public int getBalance(@AuthenticationPrincipal User user) {
        return user.getBalance();
    }

    @PostMapping(path = "/update-balance") 
    public ResponseEntity<String> updateBalance(@AuthenticationPrincipal User user, @RequestParam("balance") int balance) {
        if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }

        boolean updated = userService.updateUserBalance(user.getEmail(), balance);

        if (updated) {
            return ResponseEntity.ok("Balance updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
}
