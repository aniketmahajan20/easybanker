package com.example.demo.easybanker.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.easybanker.entity.Token;

import jakarta.transaction.Transactional;


public interface TokenRepository extends JpaRepository<Token, Long>{
    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying  
    @Query("UPDATE Token t " +
            "SET t.confirmedAt = ?2 " +
            "WHERE t.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}