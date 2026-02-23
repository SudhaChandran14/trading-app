package org.ryc.app.restcontroller;

import org.ryc.app.database.entity.UserStock;
import org.ryc.app.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/userstocks")
@CrossOrigin(origins = "http://localhost:4200")
public class UserStockController {

    @Autowired
    private UserStockService userStockService;

    // GET http://localhost:8080/api/userstocks
    // Returns ALL user stocks
    @GetMapping
    public List<UserStock> getAllUserStocks() {
        return userStockService.getAllUserStocks();
    }

    // GET http://localhost:8080/api/userstocks/1
    // Returns one user stock by ID
    @GetMapping("/{id}")
    public UserStock getUserStockById(@PathVariable Long id) {
        return userStockService.getUserStockById(id).orElseThrow();
    }

    // POST http://localhost:8080/api/userstocks/buy
    // Adds stock to user portfolio when user buys
    @PostMapping("/buy")
    public UserStock buyUserStock(@RequestBody UserStock userStock) {
        return userStockService.buyUserStock(userStock);
    }

    // PUT http://localhost:8080/api/userstocks/sell/1?qty=5
    // Reduces qty from user portfolio when user sells
    @PutMapping("/sell/{id}")
    public void sellUserStock(@PathVariable Long id, @RequestParam int qty) {
        userStockService.sellUserStock(id, qty);
    }

    // DELETE http://localhost:8080/api/userstocks/1
    // Deletes a user stock by ID
    @DeleteMapping("/{id}")
    public void deleteUserStock(@PathVariable Long id) {
        userStockService.deleteUserStock(id);
    }
}