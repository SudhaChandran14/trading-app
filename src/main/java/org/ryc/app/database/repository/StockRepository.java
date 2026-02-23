package org.ryc.app.database.repository;

import org.ryc.app.database.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// JpaRepository gives all CRUD methods for free
// Stock = entity type, Long = type of primary key (stockId)
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    // Custom query — find stock by ticker symbol e.g. "AAPL"
    // Spring JPA auto-generates the SQL from the method name
    Stock findByStockTicker(String stockTicker);

    // Custom query — search stocks where name contains a keyword
    List<Stock> findByStockNameContainingIgnoreCase(String keyword);
    // Returns only stocks where available qty is greater than 0
    List<Stock> findByAvailableQtyGreaterThan(int qty);
}