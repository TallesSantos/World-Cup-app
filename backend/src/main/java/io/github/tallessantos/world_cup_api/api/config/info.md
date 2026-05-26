# World Cup API

## Overview

World Cup API is a RESTful backend application responsible for providing structured football World Cup data for multiple clients, including:

- mobile applications
- administrative panels
- future web clients
- integrations and external services

The API centralizes all business rules, validations, persistence logic, and data management related to tournaments, matches, players, countries, and statistics.

---

# Main Goals

The project was designed to work as a football World Cup data platform capable of serving:

- historical World Cup data
- tournament standings
- match information
- player statistics
- national teams
- media content
- highlights
- rankings
- administrative operations

---

# Architecture

```text
Clients
│
├── Mobile App
├── Admin Manager (JSF)
├── Future Web App
│
└── REST API
      │
      ├── Business Rules
      ├── Validation
      ├── Authentication
      ├── Persistence
      └── Media Management
```

---

# Technologies

| Technology | Purpose |
|---|---|
| Java 21 | Main language |
| Spring Boot 3 | Backend framework |
| Spring MVC | REST controllers |
| Spring Data JPA | Persistence |
| Hibernate | ORM |
| PostgreSQL | Database |
| Maven | Dependency management |
| Jackson | JSON serialization |
| JWT | Authentication |
| Swagger/OpenAPI | API documentation |

---

# Project Structure

```text
src/main
├── java
│    └── io/github/tallessantos/world_cup_api
│          ├── api
│          │    ├── config
│          │    ├── controller
│          │    ├── dto
│          │    ├── entity
│          │    ├── mapper
│          │    ├── repository
│          │    ├── service
│          │    └── validation
│          │
│          └── Main.java
│
└── resources
     ├── application.yml
     └── db
          └── migration
```

---

# API Base Path

All endpoints are exposed under:

```text
/api
```

Example:

```text
/api/countries
/api/players
/api/matches
/api/tournaments
```

---

# Main Modules

| Module | Description |
|---|---|
| Countries | National teams and country data |
| Players | Player profiles and statistics |
| Matches | Match data and events |
| Tournaments | World Cup editions |
| Rankings | Statistics and classifications |
| Media | Images and highlights |
| Authentication | Login and authorization |
| Admin | Administrative operations |

---

# Example Endpoint

## Countries

### Request

```http
GET /api/countries
```

### Response

```json
[
  {
    "id": "bra",
    "name": "Brazil",
    "fifaCode": "BRA"
  }
]
```

---

# REST Controller Example

```java
@RestController
@RequestMapping("/api/countries")
public class CountryController {

    @GetMapping
    public List<CountryDTO> findAll() {
        return service.findAll();
    }
}
```

---

# Service Layer

Business rules are isolated in the service layer.

Example:

```java
@Service
public class CountryService {

    public List<CountryDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
```

---

# Database

The application uses PostgreSQL as the primary database.

Recommended setup:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/world_cup
    username: postgres
    password: postgres
```

---

# JPA Configuration

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update

    properties:
      hibernate:
        format_sql: true
```

---

# Static Resources

Media files are exposed through a dedicated static resource handler.

Example:

```text
/uploads/player.png
```

Example URL:

```text
http://localhost:8080/uploads/player.png
```

---

# Authentication

Authentication is JWT-based.

The API is designed to support:

- access tokens
- refresh tokens
- role-based authorization
- protected admin endpoints

---

# Recommended Roles

| Role | Description |
|---|---|
| ADMIN | Full access |
| EDITOR | Content editing |
| MODERATOR | Partial moderation |
| VIEWER | Read-only access |

---

# Error Handling

The API follows structured JSON error responses.

Example:

```json
{
  "timestamp": "2026-05-26T12:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Country not found"
}
```

---

# API Documentation

Swagger/OpenAPI can be enabled for endpoint exploration and testing.

Example path:

```text
/swagger-ui/index.html
```

---

# Running the Project

## Build

```bash
mvn clean install
```

## Run

```bash
mvn spring-boot:run
```

---

# Recommended Environment

| Tool | Version |
|---|---|
| Java | 21 |
| Maven | 3.9+ |
| PostgreSQL | 15+ |

---

# Design Principles

The API follows these principles:

- separation of concerns
- centralized business logic
- DTO-based communication
- layered architecture
- RESTful design
- scalability
- maintainability

---

# Future Improvements

Planned future improvements include:

- caching
- websocket live match updates
- advanced statistics
- asynchronous processing
- AI-assisted search
- recommendation systems
- external football data integrations

---

# Related Modules

| Module | Purpose |
|---|---|
| Mobile App | End-user application |
| Admin Manager | Administrative panel built with JSF |
| REST API | Central backend service |

---

# Objective

The main objective of this API is to serve as a centralized football World Cup platform capable of managing historical tournament data and providing scalable access for multiple applications and administrative systems.