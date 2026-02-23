package org.ryc.app.service;

import org.ryc.app.database.entity.Transaction;
import org.ryc.app.database.entity.UserStock;
import org.ryc.app.database.repository.TransactionRepository;
import org.ryc.app.database.repository.UserStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserStockService {

    @Autowired
    private UserStockRepository userStockRepository;

    @Autowired
    private TransactionRepository transactionRepository; // To save transactions

    // Get ALL stocks owned by the user
    public List<UserStock> getAllUserStocks() {
        return userStockRepository.findAll();
    }

    // Get one user stock by its ID
    public Optional<UserStock> getUserStockById(Long id) {
        return userStockRepository.findById(id);
    }

    // Add or update stock in user portfolio when user buys
    public UserStock buyUserStock(UserStock userStock) {

        // Step 1 — Save BUY transaction record
        Transaction transaction = new Transaction();
        transaction.setStockId(userStock.getStockId());
        transaction.setStockName(userStock.getStockName());
        transaction.setStockTicker(userStock.getStockTicker());
        transaction.setTransactionType("BUY");
        transaction.setQty(userStock.getQty());
        transaction.setPrice(userStock.getPrice());
        transaction.setTotalValue(userStock.getPrice() * userStock.getQty()); // qty x price
        transactionRepository.save(transaction); // Save to transaction table

        // Step 2 — Check if user already owns this stock
        List<UserStock> existing = userStockRepository.findByStockId(userStock.getStockId());

        if (!existing.isEmpty()) {
            // User already owns this stock — just increase qty
            UserStock owned = existing.get(0);
            owned.setQty(owned.getQty() + userStock.getQty());
            return userStockRepository.save(owned);
        } else {
            // User doesn't own this stock — add new record
            return userStockRepository.save(userStock);
        }
    }

    // Reduce qty when user sells
    public void sellUserStock(Long id, int qty) {
        UserStock existing = userStockRepository.findById(id).orElseThrow();

        if (existing.getQty() < qty) {
            throw new RuntimeException("Not enough shares to sell!");
        }

        // Step 1 — Save SELL transaction record
        Transaction transaction = new Transaction();
        transaction.setStockId(existing.getStockId());
        transaction.setStockName(existing.getStockName());
        transaction.setStockTicker(existing.getStockTicker());
        transaction.setTransactionType("SELL");
        transaction.setQty(qty);
        transaction.setPrice(existing.getPrice());
        transaction.setTotalValue(existing.getPrice() * qty); // qty x price
        transactionRepository.save(transaction); // Save to transaction table

        // Step 2 — Update user stock qty
        if (existing.getQty() == qty) {
            // User is selling all shares — delete the record
            userStockRepository.deleteById(id);
        } else {
            // User is selling some shares — reduce qty
            existing.setQty(existing.getQty() - qty);
            userStockRepository.save(existing);
        }
    }

    // Delete a user stock by ID
    public void deleteUserStock(Long id) {
        userStockRepository.deleteById(id);
    }
}