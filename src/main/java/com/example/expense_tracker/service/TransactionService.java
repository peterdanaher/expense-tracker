package com.example.expense_tracker.service;

import com.example.expense_tracker.exception.TransactionNotFoundException;

import com.example.expense_tracker.mapper.TransactionMapper;

import com.example.expense_tracker.model.Transaction;

import com.example.expense_tracker.repository.TransactionRepository;

import com.example.expense_tracker.dto.TransactionRequest;
import com.example.expense_tracker.dto.TransactionResponse;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    
    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    public TransactionService(TransactionRepository repository, TransactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<TransactionResponse> getAllTransactions() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TransactionResponse createTransaction(TransactionRequest request) {
        Transaction transaction = mapper.toEntity(request);
        Transaction saved = repository.save(transaction);
        return mapper.toResponse(saved);
    }

    public TransactionResponse updateTransaction(Long id, TransactionRequest request) {
        Transaction existing = repository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        mapper.updateEntity(existing, request);
        Transaction updated = repository.save(existing);

        return mapper.toResponse(updated);
    }

    public void deleteTransaction(Long id) {
        if (!repository.existsById(id)) {
            throw new TransactionNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
