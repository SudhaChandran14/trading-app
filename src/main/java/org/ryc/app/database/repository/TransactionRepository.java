package org.ryc.app.database.repository;

import org.ryc.app.database.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Get all transactions by stock ID
    List<Transaction> findByStockId(Long stockId);

    // Get all transactions by type — BUY or SELL
    List<Transaction> findByTransactionType(String transactionType);

    // Get all transactions ordered by date — newest first
    List<Transaction> findAllByOrderByTransactionDateDesc();
}