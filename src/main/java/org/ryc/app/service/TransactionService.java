package org.ryc.app.service;

import org.ryc.app.database.entity.Transaction;
import org.ryc.app.database.repository.TransactionRepository;
import org.ryc.app.rest.dto.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Convert Entity → Response DTO
    private TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .stockId(transaction.getStockId())
                .stockName(transaction.getStockName())
                .stockTicker(transaction.getStockTicker())
                .transactionType(transaction.getTransactionType())
                .qty(transaction.getQty())
                .price(transaction.getPrice())
                .totalValue(transaction.getTotalValue())
                .transactionDate(transaction.getTransactionDate() != null ?
                        transaction.getTransactionDate().toString() : null)
                .build();
    }

    // Get ALL transactions — newest first
    public List<TransactionResponse> getAllTransactions() {
        return transactionRepository.findAllByOrderByTransactionDateDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get transactions by stock ID
    public List<TransactionResponse> getTransactionsByStockId(Long stockId) {
        return transactionRepository.findByStockId(stockId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get transactions by type — BUY or SELL
    public List<TransactionResponse> getTransactionsByType(String type) {
        return transactionRepository.findByTransactionType(type)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Save a new transaction
    public TransactionResponse saveTransaction(Transaction transaction) {
        return toResponse(transactionRepository.save(transaction));
    }
}