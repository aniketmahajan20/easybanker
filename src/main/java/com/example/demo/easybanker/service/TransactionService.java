package com.example.demo.easybanker.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.easybanker.entity.Transaction;
import com.example.demo.easybanker.entity.User;
import com.example.demo.easybanker.entity.enums.TransactionStatus;
import com.example.demo.easybanker.entity.enums.TransactionType;
import com.example.demo.easybanker.repository.TransactionRepository;
import com.example.demo.easybanker.repository.UserRepository;
import com.example.demo.easybanker.request.TransactionRequest;

@Service
public class TransactionService {

    @Autowired
    public final TransactionRepository transactionRepository;

    @Autowired
    public final UserRepository userRepository;

    @Autowired
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public boolean createNewTransaction(TransactionRequest transactionRequest, User user){
        String referenceId = UUID.randomUUID().toString();
        Transaction newTransaction = new Transaction(
            referenceId,
            transactionRequest.getTransactionType(),
            LocalDateTime.now(),
            null,
            transactionRequest.getTransactionAmount(),
            transactionRequest.getTransactionReceiver(),
            TransactionStatus.PENDING,
            user,
            null,
            null
        );
        this.saveTransaction(newTransaction);
        return processTransaction(newTransaction, user);
    }

    private boolean processTransaction(Transaction transaction, User user) {
        if (transaction.getTransactionType() == TransactionType.CREDIT) {
            return processCredit(transaction, user);
        }
        else if(transaction.getTransactionType() == TransactionType.DEBIT) {
            return processDebit(transaction, user);
        }
        else if(transaction.getTransactionType() == TransactionType.TRANSFER) {
            return processTransfer(transaction, user);
        }
        else {
            throw new IllegalStateException("Unknown Transaction Type");
        }
    }

    public boolean processDebit(Transaction transaction, User user) {
        double currentUserBalance = user.getBalance();
        double newUserBalance = currentUserBalance + transaction.getTransactionAmount();
        userService.updateUserBalance(user.getEmail(), newUserBalance);
        return true;
    }

    public boolean processCredit(Transaction transaction, User user) {
        double currentUserBalance = user.getBalance();
        double newUserBalance = currentUserBalance - transaction.getTransactionAmount();
        if (newUserBalance < 0){
            return false;
        }
        userService.updateUserBalance(user.getEmail(), newUserBalance);
        return true;
    }

    public boolean processTransfer(Transaction transaction, User user) {
        Optional<User> receivingUserOptional = userRepository.findUserByEmail(transaction.getTransactionReceiver());
        if (receivingUserOptional.isPresent()){
            User receivingUser = receivingUserOptional.get();
            double currentUserBalance = user.getBalance();
            double newUserBalance = currentUserBalance - transaction.getTransactionAmount();
            if (newUserBalance < 0){
                return false;
            }
            userService.updateUserBalance(user.getEmail(), newUserBalance);
            double currentReceivingUserBalance = receivingUser.getBalance();
            double newReceivingUserBalance = currentReceivingUserBalance + transaction.getTransactionAmount();
            userService.updateUserBalance(receivingUser.getEmail(), newReceivingUserBalance);
            return true;
        }
        return false;
    }

    private void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransaction(String referenceId){
        return transactionRepository.findByReferenceId(referenceId);
    }

    public int setTransactionStatus(String referenceId, TransactionStatus transactionStatus){
        return transactionRepository.updateTransactionStatus(referenceId, transactionStatus);
    }

    public int setTransactionCompletedAt(String referenceId) {
        return transactionRepository.updateTransactionCompletedAt(referenceId, LocalDateTime.now());
    }
}
