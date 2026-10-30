# 🏦 NexaBank — Banking REST API

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)
![Kafka](https://img.shields.io/badge/Kafka-231F20?style=flat-square&logo=apachekafka&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED?style=flat-square&logo=docker&logoColor=white)
![Tests](https://img.shields.io/badge/tests-60%2B-brightgreen?style=flat-square)

**NexaBank** — банковский REST API на Java 21 и Spring Boot: счета, карты, переводы между картами и аутентификация на JWT.

🌐 **Live demo:** [Swagger UI](https://ВСТАВЬ-СВОЙ-АДРЕС.onrender.com/swagger-ui.html)

> Демо работает на бесплатном хостинге: после простоя первый запрос может занять до минуты. Kafka на демо отключена.

![Swagger UI](docs/swagger.png)

---

## ✨ Возможности

- 🔐 **JWT-аутентификация**: access-токен (15 минут) и refresh-токен (7 дней)
- 👤 **Пользователи и роли**: регистрация, вход, роли `ADMIN` и `USER`
- 🏦 **Счета**: типы `CHECKING`, `SAVINGS`, `CREDIT`, блокировка счёта администратором
- 💳 **Карты**: типы `VISA` и `MASTERCARD`, блокировка и разблокировка
- 💸 **Транзакции**: пополнение, перевод между картами, история операций
- 🛡 **Проверка владельца**: пользователь работает только со своими счетами и картами
- ⚠️ **Обработка ошибок**: кастомные исключения и глобальный обработчик
- 📨 **Kafka**: события по транзакциям, картам и авторизации (включается переменной окружения)
- 🐳 **Docker**: запуск одной командой через docker-compose

---

## 🛠 Стек

| Категория | Технологии |
|---|---|
| Язык | Java 21 |
| Фреймворк | Spring Boot 4.0 |
| Безопасность | Spring Security 7, JWT (JJWT 0.12.3), BCrypt |
| База данных | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Сообщения | Apache Kafka (Spring Kafka) |
| Маппинг | MapStruct 1.5.5 |
| Документация | Springdoc OpenAPI (Swagger UI) |
| Тесты | JUnit 5, Mockito |
| Сборка | Gradle |
| Инфраструктура | Docker, Docker Compose |
| Прочее | Lombok |

---

## 🚀 Запуск

### Требования

- Java 21+
- Docker и Docker Compose
- PostgreSQL (только для локального запуска без Docker)

### Через Docker (рекомендуется)

```bash
git clone https://github.com/user-M72/NexaBank
cd NexaBank
cp .env.example .env      # впиши свои значения
docker-compose up --build
```

- Приложение: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

### Локальный запуск

1. Создай базу данных в PostgreSQL.
2. Создай файл `.env` в корне проекта (см. таблицу ниже).
3. Запусти приложение:

```bash
./gradlew bootRun
```

### Переменные окружения

Секреты не хранятся в репозитории. Пример значений лежит в `.env.example`.

| Переменная | Описание | Пример |
|---|---|---|
| `DB_NAME` | Имя базы данных | `BankApp` |
| `DB_USER` | Пользователь БД | `postgres` |
| `DB_PASSWORD` | Пароль БД | `change_me` |
| `JWT_SECRET` | Секрет для подписи JWT (минимум 32 символа) | `openssl rand -hex 32` |
| `KAFKA_ENABLED` | Включить отправку событий в Kafka | `false` |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Адрес брокера Kafka | `localhost:29092` |

---

## 📡 API

Полная интерактивная документация доступна в Swagger UI.

### 🔐 Аутентификация

| Метод | URL | Описание | Доступ |
|---|---|---|---|
| POST | `/api/auth/v1/register` | Регистрация | Публичный |
| POST | `/api/auth/v1/login` | Вход, получение токенов | Публичный |
| POST | `/api/auth/v1/refresh` | Обновление access-токена | Публичный |
| POST | `/api/auth/v1/logout` | Выход | Публичный |

### 👤 Пользователи

| Метод | URL | Описание | Доступ |
|---|---|---|---|
| GET | `/api/user/v1` | Список пользователей | ADMIN |
| POST | `/api/user/v1` | Создать пользователя | ADMIN |
| GET | `/api/user/v1/{userId}` | Пользователь по ID | ADMIN |
| PUT | `/api/user/v1/{userId}` | Обновить пользователя | ADMIN |
| DELETE | `/api/user/v1/{userId}` | Удалить пользователя | ADMIN |
| GET | `/api/user/v1/me` | Мой профиль | Авторизованный |
| PUT | `/api/user/v1/me/update` | Обновить профиль | Авторизованный |
| PATCH | `/api/user/v1/me/password` | Сменить пароль | Авторизованный |

### 🏷 Роли

| Метод | URL | Описание | Доступ |
|---|---|---|---|
| GET | `/api/role/v1` | Список ролей | ADMIN |
| POST | `/api/role/v1` | Создать роль | ADMIN |
| GET | `/api/role/v1/{roleId}` | Роль по ID | ADMIN |
| PUT | `/api/role/v1/{roleId}` | Обновить роль | ADMIN |
| DELETE | `/api/role/v1/{roleId}` | Удалить роль | ADMIN |

### 🏦 Счета

| Метод | URL | Описание | Доступ |
|---|---|---|---|
| POST | `/api/account/v1/create` | Создать счёт | USER |
| GET | `/api/account/v1/my` | Мои счета | USER |
| GET | `/api/account/v1/{accountId}` | Счёт по ID | USER |
| PATCH | `/api/account/v1/{accountId}/block` | Сменить статус блокировки | ADMIN |

### 💳 Карты

| Метод | URL | Описание | Доступ |
|---|---|---|---|
| POST | `/api/card/v1/create` | Создать карту | USER |
| GET | `/api/card/v1/my` | Мои карты | USER |
| GET | `/api/card/v1/cardType` | Доступные типы карт | USER |
| PATCH | `/api/card/v1/{cardId}/block` | Заблокировать карту | USER |
| PATCH | `/api/card/v1/{cardId}/unBlock` | Разблокировать карту | USER |

### 💸 Транзакции

| Метод | URL | Описание | Доступ |
|---|---|---|---|
| POST | `/api/transaction/v1/deposit` | Пополнение карты | USER |
| POST | `/api/transaction/v1/transfer` | Перевод между картами | USER |
| GET | `/api/transaction/v1/history` | История транзакций | USER |

---

## 📨 Kafka

Сервис публикует события в три топика:

| Топик | События |
|---|---|
| `transaction-events` | пополнение, перевод |
| `card-events` | создание, блокировка, разблокировка карты |
| `auth-events` | регистрация, вход |

Для локальной работы брокер поднимается через docker-compose.
Отправка включается переменной `KAFKA_ENABLED=true`. По умолчанию она выключена,
поэтому основной функционал не зависит от наличия брокера.

---

## 🏗 Структура проекта

```
src/
├── main/java/BankApp/SpringBank/
│   ├── config/        # Security, JWT-фильтр, Swagger, Kafka
│   ├── controller/    # REST-контроллеры
│   ├── dto/           # Request и Response DTO (record)
│   ├── event/         # События для Kafka
│   ├── exception/     # Кастомные исключения и глобальный обработчик
│   ├── mapper/        # MapStruct-мапперы
│   ├── model/         # JPA-сущности и enum
│   ├── repository/    # Spring Data репозитории
│   ├── service/       # Интерфейсы и реализации сервисов
│   └── util/          # DbPopulator — начальные данные при старте
└── test/java/BankApp/SpringBank/
    └── service/       # Юнит-тесты сервисов
```

---

## 🔒 Безопасность

- Stateless JWT-аутентификация, сессии не используются
- Access-токен живёт 15 минут, refresh-токен 7 дней
- Пароли хэшируются через BCrypt
- Ролевая модель доступа: `ADMIN` и `USER`
- Проверка владельца: доступ только к своим счетам и картам
- Секреты вынесены в переменные окружения, `.env` исключён из репозитория

---

## 🧪 Тестирование

```bash
./gradlew test
```

Тестами покрыты сервисы: `AuthServiceImpl`, `AccountServiceImpl`, `CardServiceImpl`,
`TransactionServiceImpl`, `JwtService`, `UserServiceImpl`, `RoleServiceImpl`.
Проверяются успешные сценарии, граничные случаи и ожидаемые исключения.

---

## 🗺 Планы

- [ ] Интеграционные тесты с Testcontainers
- [ ] Ротация refresh-токенов и blacklist при выходе
- [ ] Блокировка строки счёта при конкурентных списаниях
- [ ] Пагинация истории транзакций
- [ ] CI на GitHub Actions

---

## 👤 Автор

**Muzaffar Azamov**
GitHub: [@user-M72](https://github.com/user-M72)
Telegram: [@muzaffar_o_O](https://t.me/muzaffar_o_O)
Email: muzaffarazamov007@gmail.com

---

## 📄 Лицензия

[MIT License](LICENSE)
