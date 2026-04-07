package com.example.expense_tracker.mapper;

import com.example.expense_tracker.dto.TransactionRequest;
import com.example.expense_tracker.dto.TransactionResponse;
import com.example.expense_tracker.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    
    public Transaction toEntity(TransactionRequest request) {
        Transaction transaction = new Transaction();

        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setDate(request.getDate());
        transaction.setDescription(request.getDescription());

        return transaction;
    }

    public void updateEntity(Transaction transaction, TransactionRequest request) {
        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setDate(request.getDate());
        transaction.setDescription(request.getDescription());
    }

    public TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();

        response.setId(transaction.getId());
        response.setType(transaction.getType());
        response.setAmount(transaction.getAmount());
        response.setCategory(transaction.getCategory());
        response.setDate(transaction.getDate());
        response.setDescription(transaction.getDescription());

        return response;
    }
}
