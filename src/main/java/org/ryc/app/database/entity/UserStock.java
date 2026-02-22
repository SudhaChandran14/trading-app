package org.ryc.app.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data               // Lombok — generates all getters, setters, toString, equals
@NoArgsConstructor  // Lombok — generates empty constructor
@AllArgsConstructor // Lombok — generates constructor with all fields
@Entity             // Marks this class as a JPA entity mapped to DB table
@Table(name = "user_stock") // Maps to the "user_stock" table in MySQL
public class UserStock {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB auto increments this
    private Long id;

    @Column(name = "stock_id", nullable = false) // Links to stock table
    private Long stockId;

    @Column(name = "stock_name", nullable = false) // Stock company name
    private String stockName;

    @Column(name = "stock_ticker", nullable = false) // Stock symbol e.g. AAPL
    private String stockTicker;

    @Column(name = "qty", nullable = false) // How many shares user owns
    private Integer qty;

    @Column(name = "price", nullable = false) // Price user bought at
    private Double price;
}