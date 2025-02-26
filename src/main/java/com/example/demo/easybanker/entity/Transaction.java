package com.example.demo.easybanker.entity;

import java.time.LocalDateTime;

import com.example.demo.easybanker.entity.enums.TransactionStatus;
import com.example.demo.easybanker.entity.enums.TransactionType;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
    
    @Id
    @SequenceGenerator(
        name = "transaction_sequence",
        sequenceName = "transaction_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.AUTO,
        generator = "transaction_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String referenceId; 

    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private LocalDateTime transactionStartedAt;

    @Column
    private LocalDateTime transactionCompletedAt;

    @Column(nullable = false)
    private double transactionAmount;

    @Column(nullable = true)
    private String transactionReceiver;

    @Column(nullable = false)
    private TransactionStatus transactionStatus;

    @Column(nullable = false)
    private User transactionInitiator;

    private String transactionDescription;

    
    private String transactionRemarks;

    public Transaction() {
    }

    public Transaction(String referenceId, TransactionType transactionType, LocalDateTime transactionStartedAt, @Nullable LocalDateTime transactionCompletedAt,
             double transactionAmount, @Nullable String transactionReceiver, TransactionStatus transactionStatus, User transactionInitiator, 
             @Nullable String transactionDescription, @Nullable String transactionRemarks) {
        this.referenceId = referenceId;
        this.transactionType = transactionType;
        this.transactionStartedAt = transactionStartedAt;
        this.transactionCompletedAt = transactionCompletedAt;
        this.transactionAmount = transactionAmount;
        this.transactionReceiver = transactionReceiver;
        this.transactionStatus = transactionStatus;
        this.transactionInitiator = transactionInitiator;
        this.transactionDescription = transactionDescription;
        this.transactionRemarks = transactionRemarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDateTime getTransactionStartedAt() {
        return transactionStartedAt;
    }

    public void setTransactionStartedAt(LocalDateTime transactionStartedAt) {
        this.transactionStartedAt = transactionStartedAt;
    }

    public LocalDateTime getTransactionCompletedAt() {
        return transactionCompletedAt;
    }

    public void setTransactionCompletedAt(LocalDateTime transactionCompletedAt) {
        this.transactionCompletedAt = transactionCompletedAt;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionRemarks() {
        return transactionRemarks;
    }

    public void setTransactionRemarks(String transactionRemarks) {
        this.transactionRemarks = transactionRemarks;
    }

    public User getTransactionInitiator() {
        return transactionInitiator;
    }

    public void setTransactionInitiator(User transactionInitiator) {
        this.transactionInitiator = transactionInitiator;
    }

    public String getTransactionReceiver() {
        return transactionReceiver;
    }

    public void setTransactionReceiver(String transactionReceiver) {
        this.transactionReceiver = transactionReceiver;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public String toString() {
        return "Transactions [id=" + id + ", referenceId=" + referenceId + ", transactionAmount=" + transactionAmount + ", transactionStartedAt="
                + transactionStartedAt + ", transactionCompletedAt=" + transactionCompletedAt + ", transactionDescription=" + transactionDescription + ", transactionInitiator="
                + transactionInitiator + ", transactionReceiver=" + transactionReceiver + ", transactionRemarks="
                + transactionRemarks + ", transactionStatus=" + transactionStatus + ", transactionType=" + transactionType
                + "]";
    }
}
