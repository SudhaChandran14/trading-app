package org.ryc.app.restcontroller;

import org.ryc.app.database.entity.Stock;
import org.ryc.app.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController               // Marks this as a REST API — all methods return JSON
@RequestMapping("/api/stocks") // Base URL for all stock endpoints
@CrossOrigin(origins = "http://localhost:4200") // Allows Angular to call this API
public class StockController {

    @Autowired // Spring injects StockService automatically
    private StockService stockService;

    // GET http://localhost:8080/api/stocks
    // Returns ALL stocks — this is what Angular Page 1 calls on load
    // GET http://localhost:8080/api/stocks
// Returns only stocks with available qty > 0
    @GetMapping
    public List<Stock> getAllStocks() {
        return stockService.getAvailableStocks(); // Changed to getAvailableStocks
    }

    // GET http://localhost:8080/api/stocks/1
    // Returns one stock by its ID
    @GetMapping("/{id}")
    public Stock getStockById(@PathVariable Long id) {
        return stockService.getStockById(id).orElseThrow();
    }

    // GET http://localhost:8080/api/stocks/ticker/AAPL
    // Returns one stock by its ticker symbol
    @GetMapping("/ticker/{ticker}")
    public Stock getByTicker(@PathVariable String ticker) {
        return stockService.getStockByTicker(ticker);
    }

    // GET http://localhost:8080/api/stocks/search?keyword=apple
    // Searches stocks by name — used by the search bar on Page 1
    @GetMapping("/search")
    public List<Stock> search(@RequestParam String keyword) {
        return stockService.searchByName(keyword);
    }

    // POST http://localhost:8080/api/stocks
    // Adds a new stock to the market
    @PostMapping
    public Stock addStock(@RequestBody Stock stock) {
        return stockService.addStock(stock);
    }

    // PUT http://localhost:8080/api/stocks/1
    // Updates an existing stock by ID
    @PutMapping("/{id}")
    public Stock updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        return stockService.updateStock(id, stock);
    }

    // DELETE http://localhost:8080/api/stocks/1
    // Deletes a stock by ID
    @DeleteMapping("/{id}")
    public void deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
    }
    // PUT http://localhost:8080/api/stocks/reduce/1?qty=5
// Reduces available qty when user buys
    @PutMapping("/reduce/{stockId}")
    public Stock reduceQty(@PathVariable Long stockId, @RequestParam int qty) {
        return stockService.reduceStockQty(stockId, qty);
    }

    // PUT http://localhost:8080/api/stocks/increase/1?qty=5
// Increases available qty when user sells
    @PutMapping("/increase/{stockId}")
    public Stock increaseQty(@PathVariable Long stockId, @RequestParam int qty) {
        return stockService.increaseStockQty(stockId, qty);
    }
}