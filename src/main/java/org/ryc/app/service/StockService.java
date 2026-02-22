package org.ryc.app.service;

import org.ryc.app.database.entity.Stock;
import org.ryc.app.database.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Marks this as a Spring service — holds business logic
public class StockService {

    @Autowired // Spring auto-injects the StockRepository bean here
    private StockRepository stockRepository;

    // Get ALL stocks from the market (used by Page 1)
    public List<Stock> getAllStocks() {
        return stockRepository.findAll(); // SELECT * FROM stock
    }

    // Get one stock by its ID
    public Optional<Stock> getStockById(Long stockId) {
        return stockRepository.findById(stockId);
    }

    // Get one stock by its ticker symbol e.g. "AAPL"
    public Stock getStockByTicker(String ticker) {
        return stockRepository.findByStockTicker(ticker);
    }

    // Search stocks by name keyword (for search bar on Page 1)
    public List<Stock> searchByName(String keyword) {
        return stockRepository.findByStockNameContainingIgnoreCase(keyword);
    }

    // Add a new stock to the market
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    // Update stock price or available qty
    public Stock updateStock(Long stockId, Stock updatedStock) {
        Stock existing = stockRepository.findById(stockId).orElseThrow();
        existing.setStockName(updatedStock.getStockName());
        existing.setStockTicker(updatedStock.getStockTicker());
        existing.setStockPrice(updatedStock.getStockPrice());
        existing.setAvailableQty(updatedStock.getAvailableQty());
        return stockRepository.save(existing);
    }

    // Delete a stock by ID
    public void deleteStock(Long stockId) {
        stockRepository.deleteById(stockId);
    }
}