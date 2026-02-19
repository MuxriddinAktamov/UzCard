# UzCard Backend

## Description
UzCard Backend is a RESTful API designed to simulate a card-based payment system.
The project focuses on handling financial transactions, user accounts, and balance management,
demonstrating backend logic commonly used in real-world banking and payment systems.

---

## Tech Stack
- Java 17
- Spring Boot
- Spring Security
- PostgreSQL
- Hibernate / JPA
- Maven
- Swagger (OpenAPI)

---

## Core Concepts
- Card accounts and balances
- Financial transactions (credit / debit)
- Transaction history
- Atomic operations and data consistency

---

## Features
- User authentication and authorization
- Create and manage card accounts
- Perform balance transfers between cards
- Transaction validation and error handling
- Transaction history with pagination
- Secure handling of sensitive data

---

## Architecture
Controller → Service → Repository  
Business logic is isolated in the service layer to ensure transaction consistency.

---

## API Documentation
Swagger UI is available after running the application locally:

http://localhost:8080/swagger-ui.html

---

## How to Run
```bash
mvn clean install
mvn spring-boot:run
