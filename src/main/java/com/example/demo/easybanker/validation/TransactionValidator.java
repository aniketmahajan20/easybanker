package com.example.demo.easybanker.validation;

import com.example.demo.easybanker.entity.enums.TransactionType;
import com.example.demo.easybanker.request.TransactionRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransactionValidator implements ConstraintValidator<ValidTransaction, TransactionRequest> {

    @Override
    public boolean isValid(TransactionRequest transaction, ConstraintValidatorContext context) {
        if (transaction.getTransactionType() == TransactionType.TRANSFER &&
            (transaction.getTransactionReceiver() == null || transaction.getTransactionReceiver().isEmpty())) {
            
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Transaction receiver is required for TRANSFER type")
                   .addPropertyNode("transactionReceiver")
                   .addConstraintViolation();
            return false;
        }
        return true;
    }
}

