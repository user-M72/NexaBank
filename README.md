# 🏦 NexaBank — Banking Application

A secure and modern banking REST API built with Java & Spring Boot.

## 📌 About
NexaBank is a backend banking application that allows users to manage 
accounts, perform transactions, and track financial history securely.

## ✨ Features
- 🔐 JWT Authentication & Authorization
- 👤 User registration and login
- 🏦 Create and manage bank accounts
- 💸 Deposit, Withdrawal, Transfer operations
- 📋 Transaction history
- 💳 Card management
- 🚫 Account blocking/unblocking
- ⚠️ Custom exception handling

## 🛠 Tech Stack
- **Java 17**
- **Spring Boot 3**
- **Spring Security + JWT**
- **Spring Data JPA / Hibernate**
- **PostgreSQL**
- **Lombok**
- **MapStruct**
- **Gradle**

## 🚀 Getting Started

### Requirements
- Java 17+
- PostgreSQL
- Gradle

### Installation
# Clone the repository
git clone https://github.com/user-M72/NexaBank

# Go to project directory
cd nexabank

# Configure database in application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/DbName
    username: your_username
    password: your_password

# Run the application
mvn spring-boot:run

## 📡 API Endpoints

### Auth
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/auth/register | Register new user |
| POST | /api/auth/login | Login & get JWT token |

### Accounts
| Method | URL | Description |
|--------|-----|-------------|
| GET | /api/accounts | Get all accounts |
| POST | /api/accounts | Create account |
| GET | /api/accounts/{id} | Get account by ID |
| DELETE | /api/accounts/{id} | Delete account |

### Transactions
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/transactions/deposit | Deposit money |
| POST | /api/transactions/withdraw | Withdraw money |
| POST | /api/transactions/transfer | Transfer money |
| GET | /api/transactions/history | Transaction history |

## 📁 Project Structure
src/
├── main/java/com/nexabank/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── model/
│   ├── dto/
│   ├── security/
│   ├── exception/
│   └── mapper/
└── resources/
    └── application.yml

## 👤 Author
Name: Muzaffar
GitHub: @user-M72
LinkedIn: https://github.com/user-M72/NexaBank

## 📄 License
MIT License
