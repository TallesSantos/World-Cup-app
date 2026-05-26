# ⚽ World Cup Platform

## Overview

World Cup Platform is a football-focused ecosystem designed to centralize historical World Cup data, statistics, matches, rankings, teams, players, and media content.

The project is composed of multiple modules working together to provide:

- REST APIs
- administrative management
- media delivery
- football statistics
- tournament management
- scalable data access for multiple clients

---

# 🏗️ Project Architecture

```text
Clients
│
├── Mobile App
├── Admin Manager (JSF + PrimeFaces)
├── Future Web Applications
│
└── REST API
      │
      ├── Business Layer
      ├── Validation
      ├── Media Management
      └── Authentication
```

---

# 📦 Modules

| Module | Responsibility |
|---|---|
| API | REST endpoints and HTTP communication |
| Business | Business rules, repositories, validations, and workflows |
| Admin Manager | Administrative panel using JSF |
| Media | Static image management |
| Authentication | JWT authentication and authorization |

---

# 🚀 Technologies

| Technology | Purpose |
|---|---|
| Java 21 | Main language |
| Spring Boot 3 | Backend framework |
| Spring MVC | REST APIs |
| Jakarta Faces (JSF) | Server-side admin rendering |
| PrimeFaces | UI component library |
| JoinFaces | JSF + Spring Boot integration |
| Spring Data JPA | Database access |
| Hibernate | ORM |
| PostgreSQL | Database |
| Maven | Dependency management |
| JWT | Authentication |
| Swagger/OpenAPI | API documentation |

---

# 📁 Project Structure

```text
src
 └── main
      ├── java
      │    └── io/github/tallessantos/world_cup_api
      │          ├── api
      │          ├── business
      │          ├── admin_manager
      │          └── Main.java
      │
      ├── resources
      │    ├── application.yml
      │
      │
      └── webapp
           ├── index.xhtml

```

---

# 🌐 API

The REST API is responsible for exposing football-related data to external clients.

## Base Path

```text
/api
```

## Example Endpoints

```text
/api/countries
/api/players
/api/matches
/api/tournaments
```

---

# 🧠 Business Module

The Business module centralizes:

- business rules
- repositories
- validations
- workflows
- entity orchestration
- transactional operations
- statistics processing

It acts as the application's core domain layer.

---

# 🖥️ Admin Manager

The Admin Manager is an internal administrative panel built using:

- Jakarta Faces (JSF)
- PrimeFaces
- JoinFaces

It allows administrators to manage:

- countries
- players
- tournaments
- matches
- media content
- statistics

## Example Access

```text
http://localhost:8081/admin/players.xhtml
```

---

# 🖼️ Media and Static Resources

The platform supports public media delivery through configurable static resource mappings.

---

# ⚙️ Application Configuration

## Example `application.yml`

```yml
server:
  port: 8081

spring:
  application:
    name: 'World Cup Platform'

resource:
  storage-path: 'D:/images'
  server-side-pattern-path: '/uploads/**'

joinfaces:
  primefaces:
    theme: saga

  faces-servlet:
    url-mappings: "*.xhtml"

  faces:
    project-stage: development
```

---

# 🔍 Configuration Details

---

## 🌐 Server Port

```yml
server:
  port: 8081
```

Defines the HTTP port used by the application.

Application URL:

```text
http://localhost:8081
```

---

## 🏷️ Application Name

```yml
spring:
  application:
    name: 'World Cup Platform'
```

Defines the internal Spring Boot application name.

Used for:

- logs
- monitoring
- observability
- metrics

---

## 🖼️ Static Media Mapping

```yml
resource:
  server-side-pattern-path: '/uploads/**'
```

Defines the public URL pattern used for media access.

Example:

```text
http://localhost:8081/uploads/brazil.png
```

---

## 📂 Physical Storage Directory

```yml
resource:
  storage-path: 'D:/images'
```

Defines the physical storage folder used by the server.

Example structure:

```text
D:/images
 ├── brazil.png
 ├── argentina.png
 ├── france.png
```

---

# 🌍 Media Access Example

Physical file:

```text
D:/images/brazil.png
```

Public URL:

```text
http://localhost:8081/uploads/brazil.png
```

---

# 🛡️ Authentication

The platform is designed to support JWT-based authentication.

Planned features:

- access tokens
- refresh tokens
- role-based authorization
- protected admin endpoints

---

# 👥 Planned Roles

| Role | Description |
|---|---|
| ADMIN | Full system access |
| EDITOR | Content management |
| MODERATOR | Partial moderation |
| VIEWER | Read-only access |

---

# 📚 API Documentation

Swagger/OpenAPI support can be enabled for endpoint exploration.

Example:

```text
/swagger-ui/index.html
```

---

# ▶️ Running the Project

## 📦 Install Dependencies

```bash
mvn clean install
```

---

## 🚀 Start Application

```bash
mvn spring-boot:run
```

---

# 🛠️ Build Project

```bash
mvn clean package
```

Generated artifact:

```text
/target
```

---

# ▶️ Running the JAR

```bash
java -jar target/world-cup-api.jar
```

---

# 🧪 Recommended Environment

| Tool | Version |
|---|---|
| Java | 21 |
| Maven | 3.9+ |
| PostgreSQL | 15+ |

---

# 📌 Important Notes

## Static Resource Mapping

Avoid generic mappings like:

```text
/**
```

because they may interfere with:

- JSF
- PrimeFaces
- internal resource handlers

Recommended:

```text
/uploads/**
```

---

## Windows Paths

```text
D:/images
```

or

```text
D:\\images
```

---

## Linux Paths

```text
/var/images
```

---

# 🎯 Project Goals

The main goal of the platform is to provide a scalable and maintainable ecosystem capable of managing historical football World Cup data while serving multiple applications and administrative systems.

---

# 📜 License

Project developed for educational purposes.