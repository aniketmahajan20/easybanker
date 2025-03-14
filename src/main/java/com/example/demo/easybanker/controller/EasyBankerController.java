package com.example.demo.easybanker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.easybanker.entity.AuthenticationRequest;
import com.example.demo.easybanker.entity.AuthenticationResponse;
import com.example.demo.easybanker.entity.User;
import com.example.demo.easybanker.request.TransactionRequest;
import com.example.demo.easybanker.service.TransactionService;
import com.example.demo.easybanker.service.UserService;
import com.example.demo.easybanker.util.JwtUtil;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
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
    private TransactionService transactionService;

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
    public double getBalance(@AuthenticationPrincipal User user) {
        return user.getBalance();
    }

    @PostMapping(path = "/transact") 
    public ResponseEntity<String> createTransaciton( @AuthenticationPrincipal User user, @Valid @RequestBody TransactionRequest transactionRequest) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized access");
        }
        boolean transactionPassed = transactionService.createNewTransaction(transactionRequest, user);
        if (transactionPassed) {
            return ResponseEntity.ok("Transaction successful.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Failed to process Transaction");
        }
    }
}
