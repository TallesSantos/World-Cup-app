# Admin Manager - JSF Module

## Overview

The Admin Manager module is a web administrative panel built with Jakarta Faces (JSF) and PrimeFaces.

Its purpose is to provide an internal interface for managing the application data exposed by the REST API, allowing administrators to:

- manage countries
- manage players
- manage matches
- manage tournaments
- manage media assets
- manage statistics
- manage administrative users

The module is designed to work as a visual management layer on top of the API.

---

# Architecture

```text
Mobile App
       \
        -> Spring Boot REST API
       /
Admin Manager (JSF + PrimeFaces)
```

The Admin Manager does not directly manipulate the database.

Instead, it communicates with the REST API, which centralizes:

- business rules
- validations
- authentication
- persistence
- auditing

This avoids duplicated logic and keeps the ecosystem consistent.

---

# Technologies

| Technology | Purpose |
|---|---|
| Java 21 | Main language |
| Spring Boot 3 | Application framework |
| Jakarta Faces 4 | Server-side rendering |
| PrimeFaces | UI component library |
| JoinFaces | Spring Boot + JSF integration |
| Maven | Dependency management |

---

# Project Structure

```text
src/main
├── java
│    └── io/github/tallessantos/world_cup_api
│
├── resources
│    └── application.yml
│
└── webapp
     ├── index.xhtml
     │
     └── admin
          ├── dashboard.xhtml
          ├── countries.xhtml
          ├── players.xhtml
          ├── matches.xhtml
          └── tournaments.xhtml
```

---

# JSF Pages

All JSF pages are located inside:

```text
src/main/webapp
```

Example:

```text
src/main/webapp/admin/players.xhtml
```

Access URL:

```text
http://localhost:8081/admin/players.xhtml
```

---

# PrimeFaces

PrimeFaces is used to provide rich UI components such as:

- data tables
- dialogs
- forms
- pagination
- filters
- AJAX interactions
- charts
- file upload

Example:

```xml
<p:dataTable value="#{playerBean.players}" var="player">

    <p:column headerText="Name">
        #{player.name}
    </p:column>

</p:dataTable>
```

---

# Bean Example

```java
@Named
@ViewScoped
public class PlayerBean implements Serializable {

    private List<PlayerDTO> players;

}
```

---

# REST API Integration

The Admin Manager consumes the existing REST API instead of accessing the database directly.

Example:

```java
@Service
public class CountryService {

    private final WebClient webClient;

    public CountryService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8080/api")
                .build();
    }

    public List<CountryDTO> findAll() {

        return webClient.get()
                .uri("/countries")
                .retrieve()
                .bodyToFlux(CountryDTO.class)
                .collectList()
                .block();
    }
}
```

---

# Dependency Configuration

Main dependencies:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.joinfaces</groupId>
    <artifactId>jsf-spring-boot-starter</artifactId>
    <version>5.4.3</version>
</dependency>

<dependency>
    <groupId>org.joinfaces</groupId>
    <artifactId>primefaces-spring-boot-starter</artifactId>
    <version>5.4.3</version>
</dependency>
```

---

# Application Configuration

```yaml
server:
  port: 8081

joinfaces:
  primefaces:
    theme: saga

  faces-servlet:
    url-mappings: "*.xhtml"

  faces:
    project-stage: development
```

---

# Static Resources

Static uploads are exposed through a dedicated resource handler.

Example:

```java
registry.addResourceHandler("/uploads/**")
        .addResourceLocations(
                Path.of("uploads/")
                        .toAbsolutePath()
                        .toUri()
                        .toString()
        );
```

Example access:

```text
http://localhost:8081/uploads/player.png
```

---

# Security

The Admin Manager is intended to support authentication and authorization in future implementations.

Recommended roles:

| Role | Permissions |
|---|---|
| ADMIN | Full access |
| EDITOR | Content management |
| MODERATOR | Partial moderation |
| VIEWER | Read-only |

---

# Recommendations

## Recommended

- explicit API routes
- isolated static resource mappings
- dedicated `/uploads/**` resource path
- separate API and admin concerns

## Avoid

- generic resource handlers like `/**`
- legacy `javax.faces`
- global MVC path manipulation
- direct database access from JSF

---

# Recommended URL Structure

| Module | Path |
|---|---|
| REST API | `/api/**` |
| JSF Admin | `/admin/**` |
| Uploads | `/uploads/**` |

---

# Current Goal

The current objective of this module is to provide a stable administrative interface for managing World Cup application data through a modern Java server-side rendering stack.