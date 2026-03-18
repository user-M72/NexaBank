# 🏦 NexaBank — Banking REST API

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-green?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-ready-blue?style=flat-square&logo=docker)
![Tests](https://img.shields.io/badge/Tests-60%2B-brightgreen?style=flat-square&logo=junit5)

A secure and scalable banking REST API built with Java 21 & Spring Boot. Supports account management, transactions, card operations, and JWT-based authentication.

---

## ✨ Features

- 🔐 **JWT Authentication** — Access & Refresh token system
- 👤 **User Management** — Registration, login, role-based access (ADMIN / USER)
- 🏦 **Account Management** — CHECKING, SAVINGS, CREDIT accounts
- 💸 **Transactions** — Deposit, Withdrawal, Transfer with business rules per account type
- 💳 **Card Management** — DEBIT, CREDIT, VIRTUAL cards with daily limits
- 🚫 **Account & Card Blocking** — Block/unblock with validation
- ⚠️ **Custom Exception Handling** — Global error responses with error codes
- 🧪 **Unit Tests** — 60+ tests with JUnit 5 & Mockito
- 🐳 **Docker** — Fully containerized with docker-compose

---

## 🛠 Tech Stack

| Category | Technology |
|----------|-----------|
| Language | Java 21 |
| Framework | Spring Boot 4.0 |
| Security | Spring Security 7 + JWT (JJWT 0.12.3) |
| Database | PostgreSQL 15 |
| ORM | Spring Data JPA / Hibernate 7 |
| Mapping | MapStruct 1.5.5 |
| Testing | JUnit 5 + Mockito 5, H2 |
| Build | Gradle |
| Docs | Springdoc OpenAPI (Swagger UI) |
| Other | Lombok, Docker |

---

## 🚀 Getting Started

### Requirements
- Java 21+
- Docker & Docker Compose (recommended)
- PostgreSQL (if running locally)

### Run with Docker (Recommended)

```bash
# Clone the repository
git clone https://github.com/user-M72/NexaBank
cd NexaBank

# Start the application
docker-compose up --build
```

App will be available at: `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

### Run Locally

```bash
# Clone the repository
git clone https://github.com/user-M72/NexaBank
cd NexaBank

# Configure database in src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/BankApp
    username: your_username
    password: your_password

# Run
./gradlew bootRun
```

---

## 📡 API Endpoints

### 🔐 Auth
| Method | URL | Description | Access |
|--------|-----|-------------|--------|
| POST | `/api/auth/v1/register` | Register new user | Public |
| POST | `/api/auth/v1/login` | Login & get JWT tokens | Public |
| POST | `/api/auth/v1/refresh` | Refresh access token | Public |

### 👤 Users
| Method | URL | Description | Access |
|--------|-----|-------------|--------|
| GET | `/api/users/v1` | Get all users | ADMIN |
| GET | `/api/users/v1/{id}` | Get user by ID | ADMIN |
| POST | `/api/users/v1` | Create user | ADMIN |
| PUT | `/api/users/v1/{id}` | Update user | ADMIN |
| DELETE | `/api/users/v1/{id}` | Delete user | ADMIN |

### 🏦 Accounts
| Method | URL | Description | Access |
|--------|-----|-------------|--------|
| GET | `/api/account/v1` | Get all accounts | USER |
| GET | `/api/account/v1/{id}` | Get account by ID | USER |
| POST | `/api/account/v1` | Create account | USER |
| PUT | `/api/account/v1/{id}` | Update account | USER |
| DELETE | `/api/account/v1/{id}` | Delete account | USER |
| POST | `/api/account/v1/{id}/deposit` | Deposit money | USER |
| POST | `/api/account/v1/{id}/withdraw` | Withdraw money | USER |
| POST | `/api/account/v1/{id}/block` | Block account | ADMIN |
| POST | `/api/account/v1/{id}/unblock` | Unblock account | ADMIN |

### 💸 Transactions
| Method | URL | Description | Access |
|--------|-----|-------------|--------|
| GET | `/api/transaction/v1` | Get all transactions | USER |
| GET | `/api/transaction/v1/{id}` | Get transaction by ID | USER |
| POST | `/api/transaction/v1/deposit` | Deposit | USER |
| POST | `/api/transaction/v1/withdraw` | Withdraw | USER |
| POST | `/api/transaction/v1/transfer` | Transfer between accounts | USER |
| POST | `/api/transaction/v1/{id}/cancel` | Cancel transaction | USER |

### 💳 Cards
| Method | URL | Description | Access |
|--------|-----|-------------|--------|
| GET | `/api/card/v1` | Get all cards | USER |
| GET | `/api/card/v1/{id}` | Get card by ID | USER |
| POST | `/api/card/v1` | Create card | USER |
| POST | `/api/card/v1/{id}/block` | Block card | USER |
| POST | `/api/card/v1/{id}/unblock` | Unblock card | USER |
| DELETE | `/api/card/v1/{id}` | Delete card | USER |

---

## 🏗 Project Structure

```
src/
├── main/java/BankApp/SpringBank/
│   ├── config/          # Security config, JWT filter, UserDetails
│   ├── controller/      # REST controllers
│   ├── dto/             # Request & Response DTOs
│   │   ├── req/
│   │   └── res/
│   ├── exception/       # Custom exceptions & global handler
│   ├── mapper/          # MapStruct mappers
│   ├── model/           # JPA entities & enums
│   ├── repository/      # Spring Data repositories
│   ├── security/        # JwtService
│   ├── service/         # Service interfaces & implementations
│   └── util/            # DbPopulator (seeds admin on startup)
└── test/
    └── java/BankApp/SpringBank/
        └── service/     # Unit tests (60+ tests)
```

---

## 🔒 Security

- Stateless JWT authentication (no sessions)
- Access Token: 15 minutes expiry
- Refresh Token: 7 days expiry
- Role-based authorization: `ADMIN` and `USER`
- Passwords encrypted with BCrypt

---

## 🧪 Testing

```bash
# Run all tests
./gradlew test
```

**Test coverage includes:**
- `AuthServiceImpl` — login, register, refresh, error cases
- `AccountServiceImpl` — CRUD, deposit/withdraw per account type, block/unblock
- `CardServiceImpl` — create by card type, block/unblock
- `TransactionServiceImpl` — deposit, withdraw, transfer, cancel
- `JwtService` — token generation, validation, expiry
- `RoleServiceImpl`, `UserServiceImpl` — CRUD operations

---

## 👤 Author

**Muzaffar**  
GitHub: [@user-M72](https://github.com/user-M72)  
Project: [NexaBank](https://github.com/user-M72/NexaBank)

---

## 📄 License

MIT License
