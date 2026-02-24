package org.ryc.app.service;

import org.ryc.app.database.entity.UserStock;
import org.ryc.app.database.repository.TransactionRepository;
import org.ryc.app.database.repository.UserStockRepository;
import org.ryc.app.database.entity.Transaction;
import org.ryc.app.rest.dto.UserStockRequest;
import org.ryc.app.rest.dto.UserStockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserStockService {

    @Autowired
    private UserStockRepository userStockRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Convert Entity → Response DTO
    private UserStockResponse toResponse(UserStock userStock) {
        return UserStockResponse.builder()
                .id(userStock.getId())
                .stockId(userStock.getStockId())
                .stockName(userStock.getStockName())
                .stockTicker(userStock.getStockTicker())
                .qty(userStock.getQty())
                .price(userStock.getPrice())
                .build();
    }

    // Convert Request DTO → Entity
    private UserStock toEntity(UserStockRequest request) {
        return UserStock.builder()
                .stockId(request.getStockId())
                .stockName(request.getStockName())
                .stockTicker(request.getStockTicker())
                .qty(request.getQty())
                .price(request.getPrice())
                .build();
    }

    // Get ALL stocks owned by the user
    public List<UserStockResponse> getAllUserStocks() {
        return userStockRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get one user stock by ID
    public UserStockResponse getUserStockById(Long id) {
        return toResponse(userStockRepository.findById(id).orElseThrow());
    }

    // Buy stock — add or update user portfolio
    public UserStockResponse buyUserStock(UserStockRequest request) {

        // Step 1 — Save BUY transaction
        Transaction transaction = Transaction.builder()
                .stockId(request.getStockId())
                .stockName(request.getStockName())
                .stockTicker(request.getStockTicker())
                .transactionType("BUY")
                .qty(request.getQty())
                .price(request.getPrice())
                .totalValue(request.getPrice() * request.getQty())
                .build();
        transactionRepository.save(transaction);

        // Step 2 — Check if user already owns this stock
        List<UserStock> existing = userStockRepository.findByStockId(request.getStockId());

        if (!existing.isEmpty()) {
            // User already owns this stock — increase qty
            UserStock owned = existing.get(0);
            owned.setQty(owned.getQty() + request.getQty());
            return toResponse(userStockRepository.save(owned));
        } else {
            // User doesn't own this stock — add new record
            return toResponse(userStockRepository.save(toEntity(request)));
        }
    }

    // Sell stock — reduce qty from user portfolio
    public void sellUserStock(Long id, int qty) {
        UserStock existing = userStockRepository.findById(id).orElseThrow();

        if (existing.getQty() < qty) {
            throw new RuntimeException("Not enough shares to sell!");
        }

        // Step 1 — Save SELL transaction
        Transaction transaction = Transaction.builder()
                .stockId(existing.getStockId())
                .stockName(existing.getStockName())
                .stockTicker(existing.getStockTicker())
                .transactionType("SELL")
                .qty(qty)
                .price(existing.getPrice())
                .totalValue(existing.getPrice() * qty)
                .build();
        transactionRepository.save(transaction);

        // Step 2 — Update user stock qty
        if (existing.getQty() == qty) {
            userStockRepository.deleteById(id);
        } else {
            existing.setQty(existing.getQty() - qty);
            userStockRepository.save(existing);
        }
    }

    // Delete user stock by ID
    public void deleteUserStock(Long id) {
        userStockRepository.deleteById(id);
    }
}