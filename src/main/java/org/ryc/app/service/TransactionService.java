package org.ryc.app.service;

import org.ryc.app.database.entity.Transaction;
import org.ryc.app.database.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Get ALL transactions — newest first (used by Page 3)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAllByOrderByTransactionDateDesc();
    }

    // Get transactions by stock ID
    public List<Transaction> getTransactionsByStockId(Long stockId) {
        return transactionRepository.findByStockId(stockId);
    }

    // Get transactions by type — BUY or SELL
    public List<Transaction> getTransactionsByType(String type) {
        return transactionRepository.findByTransactionType(type);
    }

    // Save a new transaction — called when user buys or sells
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}