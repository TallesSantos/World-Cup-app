# Business Module

## Overview

The Business module is responsible for centralizing the application's business rules and domain operations.

It acts as the core layer between:

- REST controllers
- repositories
- external integrations
- validation flows
- administrative operations

The main objective of this module is to isolate all domain logic from infrastructure and presentation layers.

---

# Responsibilities

The Business module is responsible for:

- domain rules
- entity orchestration
- validation
- transactional operations
- data consistency
- DTO transformations
- business workflows
- integration coordination

---

# Architecture Role

```text
Controller Layer
        │
        ▼
Business Layer
        │
        ▼
Persistence Layer
```

The Business layer receives requests from controllers, processes the application's rules, and coordinates persistence and integrations.

---

# Main Principles

The module follows these principles:

- separation of concerns
- centralized business logic
- reusable services
- low controller responsibility
- transactional consistency
- domain-oriented organization

---

# Module Structure

```text
business
├── service
├── validation
├── mapper
├── workflow
├── strategy
├── facade
└── exception
```

---

# Service Layer

Services encapsulate domain operations.

Example responsibilities:

- create tournaments
- update players
- process rankings
- validate matches
- calculate statistics

---

# Example

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

# Validation

The module centralizes business validations.

Examples:

- duplicate tournament validation
- invalid match validation
- invalid score validation
- player consistency validation
- tournament phase validation

---

# Workflow Coordination

Complex flows are orchestrated inside the Business module.

Example:

```text
Create Tournament
    │
    ├── Validate edition
    ├── Create groups
    ├── Generate standings
    ├── Generate matches
    └── Persist tournament
```

---

# Transactions

Transactional operations are controlled at the business layer.

Example:

```java
@Transactional
public void createTournament(CreateTournamentRequest request) {
}
```

This guarantees consistency during complex operations.

---

# DTO Mapping

The module is responsible for converting:

```text
Entity <-> DTO
```

This prevents exposing internal persistence models directly to external consumers.

---

# Domain Isolation

The Business module avoids leaking infrastructure concerns into business logic.

## Avoided concerns

- HTTP details
- controller logic
- database-specific operations
- UI rendering logic

---

# Error Handling

Domain-specific exceptions are defined inside the business layer.

Examples:

- TournamentAlreadyExistsException
- InvalidMatchException
- PlayerNotFoundException
- InvalidRankingException

---

# Scalability

The module was designed to support future expansion such as:

- caching
- asynchronous processing
- messaging systems
- AI integrations
- external football APIs
- event-driven workflows

---

# Relationship With Other Modules

| Module | Responsibility |
|---|---|
| API | HTTP exposure |
| Business | Domain logic |
| Persistence | Database access |
| Admin Manager | Administrative interface |
| Mobile App | End-user consumption |

---

# Recommended Design

Controllers should remain thin.

## Correct

```text
Controller
    ↓
Service
    ↓
Repository
```

## Avoid

```text
Controller
    ↓
Repository
```

This keeps the architecture maintainable and scalable.

---

# Future Improvements

Planned improvements include:

- domain events
- CQRS support
- advanced statistics engines
- asynchronous workflows
- recommendation systems
- cache abstraction
- rule engine support

---

# Objective

The main objective of the Business module is to provide a centralized, scalable, and maintainable domain layer capable of orchestrating all core World Cup application rules and workflows independently from presentation and infrastructure concerns.