package com.example.expense_tracker.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionResponse {
    
    private long id;
    private String type;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;
    
}
