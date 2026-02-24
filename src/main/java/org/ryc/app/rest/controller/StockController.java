package org.ryc.app.rest.controller;

import org.ryc.app.rest.dto.StockRequest;
import org.ryc.app.rest.dto.StockResponse;
import org.ryc.app.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@CrossOrigin(origins = "http://localhost:4200")
public class StockController {

    @Autowired
    private StockService stockService;

    // GET http://localhost:8080/api/stocks
    @GetMapping
    public List<StockResponse> getAllStocks() {
        return stockService.getAvailableStocks();
    }

    // GET http://localhost:8080/api/stocks/1
    @GetMapping("/{id}")
    public StockResponse getStockById(@PathVariable Long id) {
        return stockService.getStockById(id);
    }

    // GET http://localhost:8080/api/stocks/ticker/AAPL
    @GetMapping("/ticker/{ticker}")
    public StockResponse getByTicker(@PathVariable String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    // GET http://localhost:8080/api/stocks/search?keyword=apple
    @GetMapping("/search")
    public List<StockResponse> search(@RequestParam String keyword) {
        return stockService.searchByName(keyword);
    }

    // POST http://localhost:8080/api/stocks
    @PostMapping
    public StockResponse addStock(@RequestBody StockRequest request) {
        return stockService.addStock(request);
    }

    // PUT http://localhost:8080/api/stocks/1
    @PutMapping("/{id}")
    public StockResponse updateStock(@PathVariable Long id, @RequestBody StockRequest request) {
        return stockService.updateStock(id, request);
    }

    // DELETE http://localhost:8080/api/stocks/1
    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
    }

    // PUT http://localhost:8080/api/stocks/reduce/1?qty=5
    @PutMapping("/reduce/{stockId}")
    public StockResponse reduceQty(@PathVariable Long stockId, @RequestParam int qty) {
        return stockService.reduceStockQty(stockId, qty);
    }

    // PUT http://localhost:8080/api/stocks/increase/1?qty=5
    @PutMapping("/increase/{stockId}")
    public StockResponse increaseQty(@PathVariable Long stockId, @RequestParam int qty) {
        return stockService.increaseStockQty(stockId, qty);
    }
}