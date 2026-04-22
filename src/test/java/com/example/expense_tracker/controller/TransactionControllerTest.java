package com.example.expense_tracker.controller;

import com.example.expense_tracker.dto.TransactionRequest;
import com.example.expense_tracker.dto.TransactionResponse;
import com.example.expense_tracker.exception.TransactionNotFoundException;
import com.example.expense_tracker.service.TransactionService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.doNothing;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import(com.example.expense_tracker.exception.GlobalExceptionHandler.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    @DisplayName("GET /api/transactions returns all transactions")
    void getAllTransactions_returnsList() throws Exception {
        TransactionResponse t1 = new TransactionResponse();
        t1.setId(1L);
        t1.setType("Expense");
        t1.setAmount(25.50);
        t1.setCategory("Food");
        t1.setDate(LocalDate.of(2026, 4, 7));
        t1.setDescription("Lunch");

        TransactionResponse t2 = new TransactionResponse();
        t2.setId(2L);
        t2.setType("Income");
        t2.setAmount(1000.00);
        t2.setCategory("Salary");
        t2.setDate(LocalDate.of(2026, 4, 1));
        t2.setDescription("Paycheck");

        given(transactionService.getAllTransactions()).willReturn(List.of(t1, t2));

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].type").value("Expense"))
                .andExpect(jsonPath("$[1].category").value("Salary"));
    }

    @Test
    @DisplayName("POST /api/transactions with valid body creates transaction")
    void createTransaction_validRequest_returnsCreatedTransaction() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setType("Expense");
        request.setAmount(42.50);
        request.setCategory("Food");
        request.setDate(LocalDate.of(2026, 4, 7));
        request.setDescription("Dinner");

        TransactionResponse response = new TransactionResponse();
        response.setId(1L);
        response.setType("Expense");
        response.setAmount(42.50);
        response.setCategory("Food");
        response.setDate(LocalDate.of(2026, 4, 7));
        response.setDescription("Dinner");

        given(transactionService.createTransaction(any(TransactionRequest.class)))
                .willReturn(response);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(42.50))
                .andExpect(jsonPath("$.category").value("Food"));
    }

    @Test
    @DisplayName("POST /api/transactions with invalid body returns 400 and validation errors")
    void createTransaction_invalidRequest_returnsBadRequest() throws Exception {
        String invalidJson = """
                {
                  "type": "",
                  "amount": 0,
                  "category": "",
                  "date": null,
                  "description": "x".repeat(256)
                }
                """.replace("\"x\".repeat(256)", "\"" + "x".repeat(256) + "\"");

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.amount").exists())
                .andExpect(jsonPath("$.category").exists())
                .andExpect(jsonPath("$.date").exists())
                .andExpect(jsonPath("$.description").exists());
    }

    @Test
    @DisplayName("PUT /api/transactions/{id} with missing id returns 404")
    void updateTransaction_missingId_returnsNotFound() throws Exception {
        TransactionRequest request = new TransactionRequest();
        request.setType("Expense");
        request.setAmount(15.00);
        request.setCategory("Coffee");
        request.setDate(LocalDate.of(2026, 4, 7));
        request.setDescription("Latte");

        given(transactionService.updateTransaction(eq(999L), any(TransactionRequest.class)))
                .willThrow(new TransactionNotFoundException(999L));

        mockMvc.perform(put("/api/transactions/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Transaction not found with id: 999"));
    }

    @Test
    @DisplayName("DELETE /api/transactions/{id} returns 200")
    void deleteTransaction_existingId_returnsOk() throws Exception {
        doNothing().when(transactionService).deleteTransaction(1L);

        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/transactions/{id} with missing id returns 404")
    void deleteTransaction_missingId_returnsNotFound() throws Exception {
        doThrow(new TransactionNotFoundException(999L))
                .when(transactionService).deleteTransaction(999L);

        mockMvc.perform(delete("/api/transactions/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Transaction not found with id: 999"));
    }
}