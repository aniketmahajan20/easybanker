package com.example.demo.easybanker.request;

import com.example.demo.easybanker.entity.enums.TransactionType;
import com.example.demo.easybanker.validation.ValidTransaction;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotNull;

@ValidTransaction
public class TransactionRequest {
    @NotNull
    private TransactionType transactionType;

    @NotNull
    private double transactionAmount;

    @Nullable
    private String transactionDescription;

    @Nullable
    private String transactionReceiver;

    @Nullable
    private String transactionRemark;

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public String getTransactionReceiver() {
        return transactionReceiver;
    }

    public void setTransactionReceiver(String transactionReceiver) {
        this.transactionReceiver = transactionReceiver;
    }

    public String getTransactionRemark() {
        return transactionRemark;
    }

    public void setTransactionRemark(String transactionRemark) {
        this.transactionRemark = transactionRemark;
    }
}
