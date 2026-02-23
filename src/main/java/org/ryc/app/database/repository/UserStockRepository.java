package org.ryc.app.database.repository;

import org.ryc.app.database.entity.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// JpaRepository gives all CRUD methods for free
// UserStock = entity type, Long = type of primary key
@Repository
public interface UserStockRepository extends JpaRepository<UserStock, Long> {

    // Get all stocks owned by user filtered by ticker
    List<UserStock> findByStockTicker(String stockTicker);

    // Get all stocks owned by user filtered by stockId
    List<UserStock> findByStockId(Long stockId);


}