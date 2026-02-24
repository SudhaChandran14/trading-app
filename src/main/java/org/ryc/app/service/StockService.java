package org.ryc.app.service;

import org.ryc.app.database.entity.Stock;
import org.ryc.app.database.repository.StockRepository;
import org.ryc.app.rest.dto.StockRequest;
import org.ryc.app.rest.dto.StockResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    // Convert Entity → Response DTO
    private StockResponse toResponse(Stock stock) {
        return StockResponse.builder()
                .stockId(stock.getStockId())
                .stockName(stock.getStockName())
                .stockTicker(stock.getStockTicker())
                .stockPrice(stock.getStockPrice())
                .availableQty(stock.getAvailableQty())
                .build();
    }

    // Convert Request DTO → Entity
    private Stock toEntity(StockRequest request) {
        return Stock.builder()
                .stockName(request.getStockName())
                .stockTicker(request.getStockTicker())
                .stockPrice(request.getStockPrice())
                .availableQty(request.getAvailableQty())
                .build();
    }

    // Get all stocks with qty > 0
    public List<StockResponse> getAvailableStocks() {
        return stockRepository.findByAvailableQtyGreaterThan(0)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Get one stock by ID
    public StockResponse getStockById(Long stockId) {
        Stock stock = stockRepository.findById(stockId).orElseThrow();
        return toResponse(stock);
    }

    // Get stock by ticker
    public StockResponse getStockByTicker(String ticker) {
        return toResponse(stockRepository.findByStockTicker(ticker));
    }

    // Search stocks by name
    public List<StockResponse> searchByName(String keyword) {
        return stockRepository.findByStockNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // Add new stock
    public StockResponse addStock(StockRequest request) {
        return toResponse(stockRepository.save(toEntity(request)));
    }

    // Update stock
    public StockResponse updateStock(Long stockId, StockRequest request) {
        Stock existing = stockRepository.findById(stockId).orElseThrow();
        existing.setStockName(request.getStockName());
        existing.setStockTicker(request.getStockTicker());
        existing.setStockPrice(request.getStockPrice());
        existing.setAvailableQty(request.getAvailableQty());
        return toResponse(stockRepository.save(existing));
    }

    // Delete stock
    public void deleteStock(Long stockId) {
        stockRepository.deleteById(stockId);
    }

    // Reduce qty when user buys
    public StockResponse reduceStockQty(Long stockId, int qty) {
        Stock existing = stockRepository.findById(stockId).orElseThrow();
        if (existing.getAvailableQty() < qty) {
            throw new RuntimeException("Not enough stock available!");
        }
        existing.setAvailableQty(existing.getAvailableQty() - qty);
        return toResponse(stockRepository.save(existing));
    }

    // Increase qty when user sells
    public StockResponse increaseStockQty(Long stockId, int qty) {
        Stock existing = stockRepository.findById(stockId).orElseThrow();
        existing.setAvailableQty(existing.getAvailableQty() + qty);
        return toResponse(stockRepository.save(existing));
    }
}