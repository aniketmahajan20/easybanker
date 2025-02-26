package com.example.demo.easybanker.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.easybanker.entity.Transaction;
import com.example.demo.easybanker.entity.enums.TransactionStatus;

import jakarta.transaction.Transactional;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    Optional<Transaction>  findByReferenceId(String referenceId);

    @Transactional
    @Modifying  
    @Query("UPDATE Transaction t " +
            "SET t.transactionStatus = ?2 " +
            "WHERE t.referenceId = ?1")
    int updateTransactionStatus(String referenceId, TransactionStatus transactionStatus);

    @Transactional
    @Modifying  
    @Query("UPDATE Transaction t " +
            "SET t.transactionCompletedAt = ?2 " +
            "WHERE t.referenceId = ?1")
    int updateTransactionCompletedAt(String referenceId, LocalDateTime transactionCompletedAt);
} 
