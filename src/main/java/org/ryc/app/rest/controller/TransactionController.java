package org.ryc.app.rest.controller;

import org.ryc.app.rest.dto.TransactionResponse;
import org.ryc.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // GET http://localhost:8080/api/transactions
    // Returns ALL transactions — newest first
    @GetMapping
    public List<TransactionResponse> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // GET http://localhost:8080/api/transactions/stock/1
    // Returns all transactions for a specific stock
    @GetMapping("/stock/{stockId}")
    public List<TransactionResponse> getByStockId(@PathVariable Long stockId) {
        return transactionService.getTransactionsByStockId(stockId);
    }

    // GET http://localhost:8080/api/transactions/type/BUY
    // Returns all BUY or SELL transactions
    @GetMapping("/type/{type}")
    public List<TransactionResponse> getByType(@PathVariable String type) {
        return transactionService.getTransactionsByType(type);
    }
}
