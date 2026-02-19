# UzCard – Transaction-Based Payment Backend (Spring Boot)

## Description
UzCard is a Spring Boot backend project that simulates a card-based payment system.
The main focus of this project is **transaction processing**, including balance validation,
atomic operations, and rollback handling to ensure data consistency.

This project demonstrates real-world backend skills used in payment and fintech systems:
secure APIs, transactional business logic, and reliable persistence with PostgreSQL.

---

## Tech Stack
- Java
- Spring Boot
- Spring Security
- PostgreSQL
- Hibernate / JPA
- Maven
- Swagger (OpenAPI)

---

## Core Concepts
- Card accounts and balances
- Transfers (credit / debit)
- Transaction history
- Data consistency and atomic operations

---

## Key Features
- User authentication and authorization
- Create and manage card accounts
- Balance transfer between cards
- **Balance checks** before each transfer (prevents overdrafts)
- **Transactional operations with `@Transactional`**
- **Rollback on failure** to maintain data integrity
- Global exception handling and input validation
- Transaction history with pagination

---

## Transaction Handling
- Business logic is isolated in the service layer
- Operations are executed inside a transaction (`@Transactional`)
- If any step fails (validation / persistence), the system rolls back changes automatically

---

## Architecture
Controller → Service → Repository  
DTO pattern is used for clean and secure data transfer.

---

## API Documentation
Swagger UI is available after running the application locally:

http://localhost:8080/swagger-ui.html

---

## How to Run
```bash
mvn clean install
mvn spring-boot:run
