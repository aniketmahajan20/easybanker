package com.example.demo.easybanker.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.easybanker.entity.Token;
import com.example.demo.easybanker.entity.User;
import com.example.demo.easybanker.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenService = tokenService;
    }
    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    // public String execute_transaction(User user, TransactionRequest transactionRequest) {
    //     String reference_id  = 
    // }

    public String signUpUser(User user) {
        boolean userExists = userRepository.findUserByEmail(user.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("User already exists.");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        Token confirmationToken = new Token(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(10),
            user
        );
        tokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableUser(String email){
        return userRepository.enableUser(email);
    }

    public double getBalance(User user){
        return user.getBalance();
    }

    @Transactional
    public boolean updateUserBalance(String email, double newBalance) {
        Optional<User> userOptional  = userRepository.findUserByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBalance(newBalance);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
