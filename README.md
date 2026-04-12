# 🏦 NexaBank — Banking REST API

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-green?style=flat-square&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=flat-square&logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-ready-blue?style=flat-square&logo=docker)
![License](https://img.shields.io/badge/License-MIT-lightgrey?style=flat-square)

[//]: # (![Tests]&#40;https://img.shields.io/badge/Tests-60%2B-brightgreen?style=flat-square&logo=junit5&#41;)


**NexaBank** — это безопасный и масштабируемый банковский REST API на Java 21 и Spring Boot.  
Реализованы управление счетами, операции с картами, переводы и JWT-аутентификация.

---

## ✨ Возможности

- 🔐 **JWT аутентификация** — Access + Refresh токены, автообновление
- 👤 **Управление пользователями** — регистрация, вход, роли `ADMIN` / `USER`
- 🏦 **Счета** — типы `CHECKING`, `SAVINGS`, `CREDIT` с бизнес-правилами для каждого
- 💸 **Транзакции** — пополнение, снятие, перевод между картами
- 💳 **Карты** — типы `DEBIT`, `CREDIT`, `VIRTUAL`, суточные лимиты
- 🚫 **Блокировка** — блокировка/разблокировка счётов и карт
- ⚠️ **Обработка ошибок** — глобальный хэндлер с кодами ошибок
- 🧪 **60+ тестов** — JUnit 5 + Mockito, покрытие всех сервисов
- 🐳 **Docker** — полная контейнеризация через docker-compose

---

## 🛠 Стек технологий

| Категория     | Технология                          |
|---------------|--------------------------------------|
| Язык          | Java 21                              |
| Фреймворк     | Spring Boot 4.0                      |
| Безопасность  | Spring Security 7 + JWT (JJWT 0.12.3)|
| База данных   | PostgreSQL 15                        |
| ORM           | Spring Data JPA / Hibernate 7        |
| Маппинг       | MapStruct 1.5.5                      |
| Тестирование  | JUnit 5 + Mockito 5, H2              |
| Сборка        | Gradle                               |
| Документация  | Springdoc OpenAPI (Swagger UI)       |
| Прочее        | Lombok, Docker                       |

---

## 🚀 Запуск

### Требования

- Java 21+
- Docker & Docker Compose (рекомендуется)
- PostgreSQL (при локальном запуске)

### Через Docker (рекомендуется)

```bash
git clone https://github.com/user-M72/NexaBank
cd NexaBank
docker-compose up --build
```

Приложение: `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

### Локальный запуск

```bash
git clone https://github.com/user-M72/NexaBank
cd NexaBank
```

Настрой базу данных в `src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/BankApp
    username: your_username
    password: your_password
```

```bash
./gradlew bootRun
```

---

## 📡 API Эндпоинты

### 🔐 Аутентификация

| Метод | URL | Описание | Доступ |
|-------|-----|----------|--------|
| POST | `/api/auth/v1/register` | Регистрация | Публичный |
| POST | `/api/auth/v1/login` | Вход, получение токенов | Публичный |
| POST | `/api/auth/v1/refresh` | Обновление access токена | Публичный |

### 👤 Пользователи

| Метод  | URL                     | Описание                  | Доступ |
|--------|-------------------------|---------------------------|--------|
| GET    | `/api/users/v1`         | Все пользователи          | ADMIN  |
| GET    | `/api/users/v1/{id}`    | Пользователь по ID        | ADMIN  |
| POST   | `/api/users/v1`         | Создать пользователя      | ADMIN  |
| PUT    | `/api/users/v1/{id}`    | Обновить пользователя     | ADMIN  |
| DELETE | `/api/users/v1/{id}`    | Удалить пользователя      | ADMIN  |
| GET    | `/api/users/v1/me`      | Мой профиль               | USER   |
| PUT    | `/api/users/v1/me/update`   | Обновить профиль      | USER   |
| PATCH  | `/api/users/v1/me/password` | Сменить пароль        | USER   |

### 🏦 Счета

| Метод | URL | Описание | Доступ |
|-------|-----|----------|--------|
| GET | `/api/account/v1` | Мои счета | USER |
| GET | `/api/account/v1/{id}` | Счёт по ID | USER |
| POST | `/api/account/v1` | Создать счёт | USER |
| PUT | `/api/account/v1/{id}` | Обновить счёт | USER |
| DELETE | `/api/account/v1/{id}` | Удалить счёт | USER |
| POST | `/api/account/v1/{id}/deposit` | Пополнить | USER |
| POST | `/api/account/v1/{id}/withdraw` | Снять средства | USER |
| POST | `/api/account/v1/{id}/block` | Заблокировать | ADMIN |
| POST | `/api/account/v1/{id}/unblock` | Разблокировать | ADMIN |

### 💸 Транзакции

| Метод | URL | Описание | Доступ |
|-------|-----|----------|--------|
| GET | `/api/transaction/v1` | История транзакций | USER |
| GET | `/api/transaction/v1/{id}` | Транзакция по ID | USER |
| POST | `/api/transaction/v1/deposit` | Пополнение | USER |
| POST | `/api/transaction/v1/withdraw` | Снятие | USER |
| POST | `/api/transaction/v1/transfer` | Перевод между картами | USER |
| POST | `/api/transaction/v1/{id}/cancel` | Отмена транзакции | USER |

### 💳 Карты

| Метод | URL | Описание | Доступ |
|-------|-----|----------|--------|
| GET | `/api/card/v1` | Мои карты | USER |
| GET | `/api/card/v1/{id}` | Карта по ID | USER |
| POST | `/api/card/v1` | Создать карту | USER |
| POST | `/api/card/v1/{id}/block` | Заблокировать карту | USER |
| POST | `/api/card/v1/{id}/unblock` | Разблокировать карту | USER |
| DELETE | `/api/card/v1/{id}` | Удалить карту | USER |

---

## 🏗 Структура проекта

```
src/
├── main/java/BankApp/SpringBank/
│   ├── config/          # Security config, JWT filter, UserDetails
│   ├── controller/      # REST контроллеры
│   ├── dto/             # DTO (record классы)
│   │   ├── req/         # Request DTO
│   │   └── res/         # Response DTO
│   ├── exception/       # Кастомные исключения и глобальный хэндлер
│   ├── mapper/          # MapStruct мапперы
│   ├── model/           # JPA сущности и enum'ы
│   │   ├── baseDomain/  # BaseDomain с аудитом (UUID, createdDate и т.д.)
│   │   └── Enum/        # AccountType, CardType, Currency, TransactionType...
│   ├── repository/      # Spring Data репозитории
│   ├── service/         # Интерфейсы и реализации сервисов
│   └── util/            # DbPopulator — сид admin пользователя при старте
└── test/
    └── java/BankApp/SpringBank/
        └── service/     # Юнит тесты (60+ тестов)
```

---

## 🔒 Безопасность

- Stateless JWT аутентификация (без сессий)
- Access Token: срок жизни **15 минут**
- Refresh Token: срок жизни **7 дней**
- Ролевая авторизация: `ADMIN` и `USER`
- Пароли хешируются через **BCrypt**
- Ownership-проверка: пользователь имеет доступ только к своим счетам и картам

---

## 🧪 Тестирование

```bash
./gradlew test
```

Покрытие тестами:

- `AuthServiceImpl` — логин, регистрация, обновление токена, ошибки
- `AccountServiceImpl` — CRUD, пополнение/снятие по типу счёта, блокировка
- `CardServiceImpl` — создание по типу карты, блокировка/разблокировка
- `TransactionServiceImpl` — депозит, снятие, перевод, отмена
- `JwtService` — генерация токена, валидация, истечение срока
- `UserServiceImpl`, `RoleServiceImpl` — CRUD операции

---

## 👤 Автор

**Muzaffar**  
GitHub: [@user-M72](https://github.com/user-M72)  
Проект: [NexaBank](https://github.com/user-M72/NexaBank)

---

## 📄 Лицензия

[MIT License](LICENSE)
