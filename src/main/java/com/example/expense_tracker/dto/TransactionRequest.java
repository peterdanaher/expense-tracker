package com.example.expense_tracker.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TransactionRequest {

    @NotBlank
    private String type;

    @Positive
    private double amount;

    @NotBlank
    private String category;

    @NotNull
    private LocalDate date;

    @Size(max = 255)
    private String description;
}
