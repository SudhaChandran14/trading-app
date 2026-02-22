package org.ryc.app.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data               // Lombok — generates all getters, setters, toString, equals
@NoArgsConstructor  // Lombok — generates empty constructor
@AllArgsConstructor // Lombok — generates constructor with all fields
@Entity             // Marks this class as a JPA entity mapped to DB table
@Table(name = "stock") // Maps to the "stock" table in MySQL
public class Stock {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB auto increments this
    @Column(name = "stock_id")
    private Long stockId;

    @Column(name = "stock_name", nullable = false)
    private String stockName;

    @Column(name = "stock_ticker", nullable = false, length = 10)
    private String stockTicker;

    @Column(name = "stock_price", nullable = false)
    private Double stockPrice;

    @Column(name = "available_qty", nullable = false)
    private Integer availableQty;
}