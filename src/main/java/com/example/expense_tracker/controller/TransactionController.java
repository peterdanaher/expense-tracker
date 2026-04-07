package com.example.expense_tracker.controller;

import com.example.expense_tracker.model.Transaction;
import com.example.expense_tracker.dto.TransactionRequest;
import com.example.expense_tracker.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {
    
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

    @PostMapping
    public Transaction createTransaction(@Valid @RequestBody TransactionRequest request) {
        return service.createTransaction(request);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request
    ) {
        return service.updateTransaction(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        service.deleteTransaction(id);
    }
}
