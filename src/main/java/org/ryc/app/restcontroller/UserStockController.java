package org.ryc.app.restcontroller;

import org.ryc.app.database.entity.UserStock;
import org.ryc.app.service.UserStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController                  // Marks this as a REST API — all methods return JSON
@RequestMapping("/api/userstocks") // Base URL for all user stock endpoints
@CrossOrigin(origins = "http://localhost:4200") // Allows Angular to call this API
public class UserStockController {

    @Autowired // Spring injects UserStockService automatically
    private UserStockService userStockService;

    // GET http://localhost:8080/api/userstocks
    // Returns ALL user stocks — used by Page 2 Part 1
    @GetMapping
    public List<UserStock> getAllUserStocks() {
        return userStockService.getAllUserStocks();
    }

    // GET http://localhost:8080/api/userstocks/1
    // Returns one user stock by its ID
    @GetMapping("/{id}")
    public UserStock getUserStockById(@PathVariable Long id) {
        return userStockService.getUserStockById(id).orElseThrow();
    }

    // POST http://localhost:8080/api/userstocks
    // Adds a new stock to user portfolio (when user buys)
    @PostMapping
    public UserStock addUserStock(@RequestBody UserStock userStock) {
        return userStockService.addUserStock(userStock);
    }

    // PUT http://localhost:8080/api/userstocks/1
    // Updates an existing user stock by ID
    @PutMapping("/{id}")
    public UserStock updateUserStock(@PathVariable Long id, @RequestBody UserStock userStock) {
        return userStockService.updateUserStock(id, userStock);
    }

    // DELETE http://localhost:8080/api/userstocks/1
    // Deletes a user stock by ID (when user sells)
    @DeleteMapping("/{id}")
    public void deleteUserStock(@PathVariable Long id) {
        userStockService.deleteUserStock(id);
    }
}