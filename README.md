Demo video link - https://drive.google.com/file/d/1nms303KTnPQODNxaEMmue2Fqt04dvVj4/view?usp=drive_link
Trading application Doc:
**TRADECORE**

	Full-Stack Trading Application

	Project Documentation

	Spring Boot  |  Angular  |  MySQL

  

# 1. Project Overview

TradeCore is a full-stack web application that allows users to view stocks available in the market, buy and sell stocks, and track their transaction history. The application is built using Spring Boot for the backend REST API, Angular for the frontend, and MySQL as the database.

## 1.1 Technology Stack

|   |   |   |
|---|---|---|
|**Layer**|**Technology**|**Port**|
|Backend|Spring Boot (Java)|8080|
|Frontend|Angular (TypeScript)|4200|
|Database|MySQL|3306|
|ORM|Spring Data JPA / Hibernate|-|
|IDE (Backend)|IntelliJ IDEA|-|
|IDE (Frontend)|Visual Studio Code|-|

## 1.2 Architecture

The application follows a 3-tier architecture:

•        Frontend (Angular) — Runs on port 4200, communicates with the backend via HTTP

•        Backend (Spring Boot) — Runs on port 8080, exposes REST API endpoints

•        Database (MySQL) — Runs on port 3306, stores all application data

  

# 2. Database Design

The application uses MySQL database named 'trading_db' with 3 tables.

## 2.1 stock Table

Stores all stocks available in the market.

|               |               |                                     |
| ------------- | ------------- | ----------------------------------- |
| **Column**    | **Type**      | **Description**                     |
| stock_id      | BIGINT (PK)   | Auto-generated unique ID            |
| stock_name    | VARCHAR(100)  | Full company name e.g. Apple Inc.   |
| stock_ticker  | VARCHAR(10)   | Short symbol e.g. AAPL              |
| stock_price   | DECIMAL(10,2) | Current price in USD                |
| available_qty | INT           | How many shares available in market |

## 2.2 user_stock Table

Stores stocks owned by the user.

|              |               |                           |
| ------------ | ------------- | ------------------------- |
| **Column**   | **Type**      | **Description**           |
| id           | BIGINT (PK)   | Auto-generated unique ID  |
| stock_id     | BIGINT (FK)   | Links to stock table      |
| stock_name   | VARCHAR(100)  | Stock company name        |
| stock_ticker | VARCHAR(10)   | Stock symbol e.g. AAPL    |
| qty          | INT           | How many shares user owns |
| price        | DECIMAL(10,2) | Price user bought at      |

## 2.3 transaction Table

Records every buy and sell transaction.

|                  |               |                              |
| ---------------- | ------------- | ---------------------------- |
| **Column**       | **Type**      | **Description**              |
| id               | BIGINT (PK)   | Auto-generated unique ID     |
| stock_id         | BIGINT        | Which stock was traded       |
| stock_name       | VARCHAR(100)  | Stock company name           |
| stock_ticker     | VARCHAR(10)   | Stock symbol                 |
| transaction_type | VARCHAR(4)    | BUY or SELL                  |
| qty              | INT           | How many shares traded       |
| price            | DECIMAL(10,2) | Price at time of transaction |
| total_value      | DECIMAL(10,2) | qty x price                  |
| transaction_date | DATETIME      | Auto-set date and time       |

  

# 3. Backend — Spring Boot

The backend is a Spring Boot REST API application built with Java. It uses Spring Data JPA to communicate with MySQL and exposes REST endpoints consumed by Angular.

## 3.1 Project Package Structure

org.ryc.app

├── database/

│   ├── entity/          → Stock.java, UserStock.java, Transaction.java

│   └── repository/      → StockRepository, UserStockRepository, TransactionRepository

├── restcontroller/      → StockController, UserStockController, TransactionController

├── service/             → StockService, UserStockService, TransactionService

└── TradingAppApplication.java

## 3.2 REST API Endpoints

**Stock Endpoints**

|            |                                |                              |
| ---------- | ------------------------------ | ---------------------------- |
| **Method** | **URL**                        | **Description**              |
| GET        | /api/stocks                    | Get all stocks with qty > 0  |
| GET        | /api/stocks/{id}               | Get one stock by ID          |
| GET        | /api/stocks/ticker/{ticker}    | Get stock by ticker          |
| GET        | /api/stocks/search?keyword=    | Search stocks by name        |
| POST       | /api/stocks                    | Add new stock                |
| PUT        | /api/stocks/{id}               | Update stock                 |
| PUT        | /api/stocks/reduce/{id}?qty=   | Reduce qty when user buys    |
| PUT        | /api/stocks/increase/{id}?qty= | Increase qty when user sells |
| DELETE     | /api/stocks/{id}               | Delete stock                 |

**User Stock Endpoints**

|            |                                |                                    |
| ---------- | ------------------------------ | ---------------------------------- |
| **Method** | **URL**                        | **Description**                    |
| GET        | /api/userstocks                | Get all user stocks                |
| GET        | /api/userstocks/{id}           | Get one user stock by ID           |
| POST       | /api/userstocks/buy            | Buy stock — add to portfolio       |
| PUT        | /api/userstocks/sell/{id}?qty= | Sell stock — reduce from portfolio |
| DELETE     | /api/userstocks/{id}           | Delete user stock                  |

**Transaction Endpoints**

|            |                               |                                     |
| ---------- | ----------------------------- | ----------------------------------- |
| **Method** | **URL**                       | **Description**                     |
| GET        | /api/transactions             | Get all transactions (newest first) |
| GET        | /api/transactions/stock/{id}  | Get transactions by stock ID        |
| GET        | /api/transactions/type/{type} | Get transactions by BUY or SELL     |
| POST       | /api/transactions             | Save new transaction                |

## 3.3 Key Java Annotations Used

|                        |                                                        |
| ---------------------- | ------------------------------------------------------ |
| **Annotation**         | **Description**                                        |
| @SpringBootApplication | Marks the main entry point of the Spring Boot app      |
| @Entity                | Marks a class as a JPA entity mapped to a DB table     |
| @Table(name=)          | Maps entity to a specific table name in MySQL          |
| @Id                    | Marks the primary key field                            |
| @GeneratedValue        | Auto-increments the ID value                           |
| @Column(name=)         | Maps a field to a specific column in the table         |
| @Repository            | Marks interface as a Spring Data repository            |
| @Service               | Marks class as a Spring service (business logic layer) |
| @RestController        | Marks class as a REST API controller                   |
| @RequestMapping        | Sets the base URL for all endpoints in a controller    |
| @CrossOrigin           | Allows Angular (port 4200) to call the API             |
| @GetMapping            | Maps HTTP GET requests to a method                     |
| @PostMapping           | Maps HTTP POST requests to a method                    |
| @PutMapping            | Maps HTTP PUT requests to a method                     |
| @DeleteMapping         | Maps HTTP DELETE requests to a method                  |
| @Autowired             | Automatically injects Spring beans                     |
| @Data (Lombok)         | Auto-generates getters, setters, toString, equals      |
| @NoArgsConstructor     | Auto-generates empty constructor                       |
| @AllArgsConstructor    | Auto-generates constructor with all fields             |
| @PrePersist            | Runs a method before saving to DB                      |

  

# 4. Frontend — Angular

The frontend is an Angular standalone application that communicates with the Spring Boot REST API using HttpClient. It consists of 3 pages navigated via a top navbar.

## 4.1 Project Structure

src/app/

├── components/

│   ├── stock-list/      → Page 1 — Market Stocks

│   ├── trade/           → Page 2 — Trade (Buy & Sell)

│   └── transaction/     → Page 3 — Transaction History

├── models/

│   ├── stock.model.ts

│   ├── user-stock.model.ts

│   └── transaction.model.ts

├── services/

│   ├── stock.service.ts

│   ├── user-stock.service.ts

│   └── transaction.service.ts

├── app.routes.ts

├── app.config.ts

├── app.ts

└── app.html

## 4.2 Pages

|          |                      |               |                                                    |
| -------- | -------------------- | ------------- | -------------------------------------------------- |
| **Page** | **Component**        | **URL**       | **Description**                                    |
| Page 1   | StockListComponent   | /stocks       | Shows all stocks available in market with search   |
| Page 2   | TradeComponent       | /trade        | Buy stocks from market, sell user portfolio stocks |
| Page 3   | TransactionComponent | /transactions | Shows full transaction history                     |

## 4.3 Page 1 — Market Stocks

Displays all stocks available in the market fetched from GET /api/stocks. Stocks with zero available quantity are hidden. Includes a search bar to filter by stock name or ticker.

|               |                                                       |
| ------------- | ----------------------------------------------------- |
| **Field**     | **Description**                                       |
| Stock ID      | Unique ID from DB                                     |
| Stock Name    | Full company name                                     |
| Stock Ticker  | Short symbol e.g. AAPL                                |
| Stock Price   | Current price in USD                                  |
| Available Qty | Number of shares available with HIGH/MEDIUM/LOW label |

## 4.4 Page 2 — Trade

Split into two parts. Part 1 shows the user's portfolio with a SELL option. Part 2 shows all market stocks with a BUY option. Both parts include a quantity input box.

Buy Flow:

•        User enters qty in input box next to the stock

•        Clicks BUY button

•        Angular calls PUT /api/stocks/reduce/{id}?qty= to reduce market stock qty

•        Angular calls POST /api/userstocks/buy to add stock to user portfolio

•        Transaction is automatically saved to the transaction table

Sell Flow:

•        User enters qty in input box next to their stock

•        Clicks SELL button

•        Angular calls PUT /api/stocks/increase/{id}?qty= to increase market stock qty

•        Angular calls PUT /api/userstocks/sell/{id}?qty= to reduce from portfolio

•        Transaction is automatically saved to the transaction table

## 4.5 Page 3 — Transaction History

Displays all buy and sell transactions fetched from GET /api/transactions ordered by newest first.

|             |                                  |
| ----------- | -------------------------------- |
| **Field**   | **Description**                  |
| ID          | Transaction ID                   |
| Date        | Date and time of transaction     |
| Stock Name  | Company name                     |
| Ticker      | Stock symbol                     |
| Type        | BOUGHT or SOLD badge             |
| Qty         | Number of shares traded          |
| Price       | Price per share at time of trade |
| Total Value | qty x price                      |

  

# 5. How to Run the Application

## 5.1 Prerequisites

•        Java 17 or higher installed

•        Node.js and npm installed

•        MySQL installed and running

•        IntelliJ IDEA installed

•        Visual Studio Code installed

•        Angular CLI installed: npm install -g @angular/cli

## 5.2 Step-by-Step Setup

**Step 1 — Setup MySQL Database**

CREATE DATABASE trading_db;

USE trading_db;

-- Then run the CREATE TABLE scripts for stock, user_stock, transaction

**Step 2 — Configure Spring Boot**

Open src/main/resources/application.properties and update:

spring.datasource.url=jdbc:mysql://localhost:3306/trading_db

spring.datasource.username=root

spring.datasource.password=yourpassword

server.port=8080

**Step 3 — Run Spring Boot**

Open IntelliJ IDEA, right click TradingAppApplication.java and click Run. Wait for:

Started TradingAppApplication on port 8080

**Step 4 — Run Angular**

cd trading-frontend

ng serve

Wait for: Compiled successfully

**Step 5 — Open Browser**

http://localhost:4200

## 5.3 Application URLs

|                        |                                    |
| ---------------------- | ---------------------------------- |
| **Page**               | **URL**                            |
| Market Stocks (Page 1) | http://localhost:4200/stocks       |
| Trade (Page 2)         | http://localhost:4200/trade        |
| Transactions (Page 3)  | http://localhost:4200/transactions |
| Spring Boot API        | http://localhost:8080/api/stocks   |

  

# 6. Summary

The TradeCore application is a complete full-stack trading system with:

•        3 MySQL tables — stock, user_stock, transaction

•        3 Java entities with JPA mapping

•        3 REST controllers exposing 18 API endpoints

•        3 Angular pages with full CRUD operations

•        Automatic transaction recording on every buy and sell

•        Real-time stock quantity management

•        Search and filter functionality on Page 1

The application demonstrates a complete Spring Boot + Angular + MySQL integration following standard REST API design principles and Angular standalone component architecture.
