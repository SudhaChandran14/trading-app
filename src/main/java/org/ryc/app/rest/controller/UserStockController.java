package org.ryc.app.rest.controller;

import org.ryc.app.rest.dto.UserStockRequest;
import org.ryc.app.rest.dto.UserStockResponse;
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
    @GetMapping
    public List<UserStockResponse> getAllUserStocks() {
        return userStockService.getAllUserStocks();
    }

    // GET http://localhost:8080/api/userstocks/1
    @GetMapping("/{id}")
    public UserStockResponse getUserStockById(@PathVariable Long id) {
        return userStockService.getUserStockById(id);
    }

    // POST http://localhost:8080/api/userstocks/buy
    @PostMapping("/buy")
    public UserStockResponse buyUserStock(@RequestBody UserStockRequest request) {
        return userStockService.buyUserStock(request);
    }

    // PUT http://localhost:8080/api/userstocks/sell/1?qty=5
    @PutMapping("/sell/{id}")
    public void sellUserStock(@PathVariable Long id, @RequestParam int qty) {
        userStockService.sellUserStock(id, qty);
    }

    // DELETE http://localhost:8080/api/userstocks/1
    @DeleteMapping("/{id}")
    public void deleteUserStock(@PathVariable Long id) {
        userStockService.deleteUserStock(id);
    }
}