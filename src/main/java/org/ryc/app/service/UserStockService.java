package org.ryc.app.service;

import org.ryc.app.database.entity.UserStock;
import org.ryc.app.database.repository.UserStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Marks this as a Spring service — holds business logic
public class UserStockService {

    @Autowired // Spring auto-injects the UserStockRepository bean here
    private UserStockRepository userStockRepository;

    // Get ALL stocks owned by the user (used by Page 2 Part 1)
    public List<UserStock> getAllUserStocks() {
        return userStockRepository.findAll(); // SELECT * FROM user_stock
    }

    // Get one user stock by its ID
    public Optional<UserStock> getUserStockById(Long id) {
        return userStockRepository.findById(id);
    }

    // Add a new stock to user portfolio (when user buys)
    public UserStock addUserStock(UserStock userStock) {
        return userStockRepository.save(userStock);
    }

    // Update user stock qty or price
    public UserStock updateUserStock(Long id, UserStock updatedUserStock) {
        UserStock existing = userStockRepository.findById(id).orElseThrow();
        existing.setStockName(updatedUserStock.getStockName());
        existing.setStockTicker(updatedUserStock.getStockTicker());
        existing.setQty(updatedUserStock.getQty());
        existing.setPrice(updatedUserStock.getPrice());
        return userStockRepository.save(existing);
    }

    // Delete a user stock by ID (when user sells)
    public void deleteUserStock(Long id) {
        userStockRepository.deleteById(id);
    }
}