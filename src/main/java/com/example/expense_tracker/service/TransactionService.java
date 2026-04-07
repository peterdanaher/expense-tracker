package com.example.expense_tracker.service;

import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.repository.TransactionRepository;
import com.example.expense_tracker.dto.TransactionRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction createTransaction(TransactionRequest request) {
        Transaction transaction = new Transaction();

        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setCategory(request.getCategory());
        transaction.setDate(request.getDate());
        transaction.setDescription(request.getDescription());

        return repository.save(transaction);
    }

    public Transaction updateTransaction(Long id, TransactionRequest request) {
        Transaction existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id));

        existing.setType(request.getType());
        existing.setAmount(request.getAmount());
        existing.setCategory(request.getCategory());
        existing.setDate(request.getDate());
        existing.setDescription(request.getDescription());

        return repository.save(existing);
    }

    public void deleteTransaction(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Transaction not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
